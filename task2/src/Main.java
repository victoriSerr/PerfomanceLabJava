package ru.pl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void f(InputData inputData) {

        double cx = inputData.getSphere().getCenter()[0];
        double cy = inputData.getSphere().getCenter()[1];
        double cz = inputData.getSphere().getCenter()[2];

        double px = inputData.getLine()[0][0];
        double py = inputData.getLine()[0][1];
        double pz = inputData.getLine()[0][2];

        double ex = inputData.getLine()[1][0];
        double ey = inputData.getLine()[1][1];
        double ez = inputData.getLine()[1][2];

        double vx = ex - px;
        double vy = ey - py;
        double vz = ez - pz;

        double radius = inputData.getSphere().getRadius();
        double A = vx * vx + vy * vy + vz * vz;
        double B = 2.0 * (px * vx + py * vy + pz * vz - vx * cx - vy * cy - vz * cz);
        double C = px * px - 2 * px * cx + cx * cx + py * py - 2 * py * cy + cy * cy +
                pz * pz - 2 * pz * cz + cz * cz - radius * radius;

        // discriminant
        double D = B * B - 4 * A * C;


        if (D < 0) {
            System.out.println("Коллизий не найдено");
        } else {
            double t1 = (-B - Math.sqrt(D)) / (2.0 * A);
            double x1 = px * (1 - t1) + t1 * ex;
            double y1 = py * (1 - t1) + t1 * ey;
            double z1 = pz * (1 - t1) + t1 * ez;


            System.out.println("[" + x1 + ", " + y1 + ", " + z1 + "]");

            if (D == 0) {
                return;
            }
            double t2 = (-B + Math.sqrt(D)) / (2.0 * A);
            double x2 = px * (1 - t2) + t2 * ex;
            double y2 = py * (1 - t2) + t2 * ey;
            double z2 = pz * (1 - t2) + t2 * ez;

            System.out.println("[" + x2 + ", " + y2 + ", " + z2 + "]");

        }
    }


    @SneakyThrows
    public static void main(String[] args) {

        Scanner scanner1 = new Scanner(System.in);

        System.out.println("Введите путь к файлу:");

        String filename = scanner1.nextLine();
//        String filename = "test.txt";

        Scanner scanner2 = new Scanner(Paths.get(filename));
        String json = scanner2.nextLine();

        ObjectMapper objectMapper = new ObjectMapper();
        InputData inputData = objectMapper.readValue(json, InputData.class);

        f(inputData);
    }
}
