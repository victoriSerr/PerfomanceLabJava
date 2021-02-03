package ru.pl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Random;

public class LogGenerator {

    public static String wannaTopUp = "wanna top up";
    public static String wannaScoop = "wanna scoop";

    public static void generateLogFile() {

        Random random = new Random();

        BufferedWriter writer;
        try {

             writer = new BufferedWriter(new FileWriter("test.log"));

             int boxSize = random.nextInt(1000);
             int currentBoxSize = random.nextInt(boxSize);
             writer.write(boxSize + "\n");
             writer.write(currentBoxSize + "\n");

             int i = 0;
             while (Files.size(Paths.get("test.log")) < 1024 * 1024) {
                 String log = "";
                 LocalDateTime time = LocalDateTime.now().plusHours(i);
                 i++;
                 String username = "user" + random.nextInt(5);
                 log = time + "-" + username + "-";
                 if (random.nextBoolean()) {
                     log += wannaTopUp + " " + random.nextInt(boxSize);
                 } else {
                     log += wannaScoop + " " + random.nextInt(boxSize);
                 }

                 writer.write(log + "\n");
             }

             writer.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
