package ru.pl;

import com.opencsv.CSVWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class Statistic {

    private String logFile;
    private int totalCapacity = 0;
    private int currentCapacity = 0;
    private LocalDateTime from;
    private LocalDateTime to;

    private int tryTopUpCount = 0;
    private int topUpCapacity = 0;
    private int notTopUpCapacity = 0;
    private int topUpFails = 0;


    private int tryScoopCount = 0;
    private int scoopCapacity = 0;
    private int notScoopCapacity = 0;
    private int scoopFails = 0;

    private int capacityFromTime = -1;
    private int capacityToTime = -1;

    public Statistic(String logFile, LocalDateTime from, LocalDateTime to) {
        this.logFile = logFile;
        this.from = from;
        this.to = to;
    }

    public void getStatistic() {

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(logFile));
            totalCapacity = Integer.parseInt(reader.readLine());
            currentCapacity = Integer.parseInt(reader.readLine());

            String logLine;
            LocalDateTime logTime;
            String logUsername;
            boolean isWannaTopUp;
            int logCapacity;
            while ((logLine = reader.readLine()) != null) {
                String[] s = logLine.split("-");
                logTime = LocalDateTime.parse(s[0] + "-" + s[1] + "-" + s[2]);
                logUsername = s[3];
                if (s[4].startsWith("wanna top up")) {
                    isWannaTopUp = true;
                    logCapacity = Integer.parseInt(s[4].split(" ")[3]);
                } else {
                    isWannaTopUp = false;
                    logCapacity = Integer.parseInt(s[4].split(" ")[2]);
                }
                if (from.isAfter(logTime)) {
                    changeCurrentCapacity(isWannaTopUp, logCapacity);
                } else if (to.isAfter(logTime)) {

                    if (capacityFromTime == -1) {
                        capacityFromTime = currentCapacity;
                    }

                    if (isWannaTopUp) {
                        tryTopUpCount++;
                        if (changeCurrentCapacity(isWannaTopUp, logCapacity)) {
                            topUpCapacity += logCapacity;
                        } else {
                            topUpFails++;
                            notTopUpCapacity += logCapacity;
                        }
                    } else {
                        tryScoopCount++;
                        if (changeCurrentCapacity(isWannaTopUp, logCapacity)) {
                            scoopCapacity += logCapacity;
                        } else {
                            scoopFails++;
                            notScoopCapacity += logCapacity;
                        }
                    }
                } else if (to.isBefore(logTime) && capacityToTime == -1) {
                    capacityToTime = currentCapacity;
                }
            }

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        createCsvResultFile();
    }

    private void createCsvResultFile() {
        try (FileOutputStream os = new FileOutputStream("result.csv")) {

            os.write(0xef);
            os.write(0xbb);
            os.write(0xbf);

            CSVWriter write = new CSVWriter(new OutputStreamWriter(os, "UTF-8"));


            String[] header = {"количество попыток налить воду в бочку за указанный период",
                    "процент ошибок налить за указанный период",
                    "объем воды налить в бочку за указанный период",
                    "объем воды не налит в бочку за указанный период",
                    "количество попыток зачерпнуть воду за указанный период",
                    "процент ошибок зачерпнуть из бочки за указанный период",
                    "объем воды взятый из бочки за указанный период",
                    "объем воды не зачерпан из бочки за указанный период",
                    "объем воды был в бочке в начале указанного периода",
                    "объем воды был в бочке в конце указанного периода"
            };

            write.writeNext(header);

            int topUpFailsPercent = 0;
            int scoopFailsPercent = 0;

            if (tryTopUpCount != 0) {
                topUpFailsPercent = (topUpFails / tryTopUpCount) * 100;
            }
            if (tryScoopCount != 0) {
                scoopFailsPercent = (scoopFails / tryScoopCount) * 100;
            }

            String[] data = {tryTopUpCount + "",
                    topUpFailsPercent + "%",
                    topUpCapacity + "",
                    notTopUpCapacity + "",
                    tryScoopCount + "",
                    scoopFailsPercent + "%",
                    scoopCapacity + "",
                    notScoopCapacity + "",
                    capacityFromTime + "",
                    capacityToTime + ""
            };

            write.writeNext(data);

            write.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private boolean changeCurrentCapacity(boolean isWannaTopUp, int logCapacity) {
        if (isWannaTopUp) {
            if (totalCapacity - currentCapacity > logCapacity) {
                currentCapacity += logCapacity;
                return true;
            }
        } else {
            if (currentCapacity > logCapacity) {
                currentCapacity -= logCapacity;
                return true;
            }
        }

        return false;
    }
}
