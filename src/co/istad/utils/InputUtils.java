package co.istad.utils;

import java.util.Scanner;

import static co.istad.utils.PrintUtils.*;

public class InputUtils {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readText(String message) {
        print(">> "+message+ " : ");
        return scanner.nextLine().trim();
    }

    public static String readValidText(String message) {
        while (true) {
            String input = readText(message);
            if (!input.isEmpty()) return input;
            printErr("Input cannot be empty.");
        }
    }

    public static int readInt(String message) {
        while (true) {
            try {
                String input = readText(message);
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                printErr("Invalid number. Please enter a valid integer.");
            }
        }
    }

    public static double readDouble(String message) {
        while (true) {
            try {
                String input = readText(message);
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                printErr("Invalid price. Example : 1.50");
            }
        }
    }

    public static boolean readconfirm(String message) {
        while (true) {
            String input = readText(message + " (y/n): ").toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            printErr("Please type 'y' for Yes or 'n' for No.");
        }
    }
    public static  boolean readCustType(String message) {
        while (true) {
            printCase("Select Type: [1] Normal  [2] VIP");
            String input = readText(message);
            if (input.equals("1")) {
                return true ;
            }
            if (input.equals("2")) {
                return false;
            }

        }
    }
}