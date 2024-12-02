package edu.school21.services;

import java.util.Scanner;

public class InputHandler {
    static private final Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    static public Object readInput(Class<?> type) {
        if (type == String.class) {
            return scanner.nextLine();
        } else if (type == Integer.class || type == int.class) {
            int value = scanner.nextInt();
            scanner.nextLine();
            return value;
        } else if (type == Double.class || type == double.class) {
            double value = scanner.nextDouble();
            scanner.nextLine();
            return value;
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }
}
