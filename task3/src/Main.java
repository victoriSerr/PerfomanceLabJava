package ru.pl;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.time.LocalDateTime;

public class Main {

    static class Arguments {
        @Parameter(names = "-log", required = true)
        public static String log;

        @Parameter(names = "-from", required = true)
        public static String timeFrom;

        @Parameter(names = "-to", required = true)
        public static String timeTo;
    }

    public static void main(String[] args) {

        Arguments arguments = new Arguments();

        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);


        Statistic statistic = new Statistic(Arguments.log,
                LocalDateTime.parse(Arguments.timeFrom),
                LocalDateTime.parse(Arguments.timeTo));

        statistic.getStatistic();
    }
}
