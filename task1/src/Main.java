package ru.pl;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static String itoBase(String nb, int base) {
        BigInteger a = new BigInteger(nb);
        return a.toString(base);
    }

    public static String itoBase(String nb, int baseSrs, int baseDst) {
        BigInteger a = new BigInteger(nb, baseSrs);
        return a.toString(baseDst);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select mode:");
        System.out.println("1 - из десятичной системы в любую другую");
        System.out.println("2 - из любой системы в любую другую");

        String mode = scanner.nextLine();



        if (mode.equals("1")) {
            String num = scanner.nextLine();
            String regex = "\\d+";
            if (num.matches(regex)) {
                int baseSrc = scanner.nextInt();
                System.out.println(itoBase(num, baseSrc));
            } else {
                throw new IllegalArgumentException("Не корректно введено число");
            }
        } else if (mode.equals("2")) {
            String num = scanner.nextLine();
            String regex = "(\\d*[a-z]*)+";
            if (num.matches(regex)) {
                int baseSrc = scanner.nextInt();
                int baseDst = scanner.nextInt();
                System.out.println(itoBase(num, baseSrc, baseDst));
            } else {
                throw new IllegalArgumentException("Не корректно введено число");
            }
        } else throw new IllegalArgumentException("Такого мода не существует");

    }
}
