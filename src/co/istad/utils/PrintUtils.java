package co.istad.utils;

import static co.istad.utils.InputUtils.readEnter;
import static co.istad.utils.InputUtils.readText;

public class PrintUtils {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";

    public static void print(Object msg) {
        System.out.print(msg);
    }
    public static void println(Object msg) {
        System.out.println(msg);
    }
    public static void println() {
        System.out.println();
    }

    public static void printTrue(String msg) {
        System.out.println(GREEN + "[+] " + msg + RESET);
    }

    public static void printErr(String msg) {
        System.out.println(RED + "[X] " + msg + RESET);
    }

    public static void printInfo(String msg) {
        System.out.println(BLUE + "[i] " + msg + RESET);
    }

    public static void printWarm(String msg) {
        System.out.println(YELLOW + "[!] " + msg + RESET);
    }

    public static void printHead(String title) {
        System.out.println(CYAN + "\n*** " + title.toUpperCase() +" ***" + RESET);
    }

    public static void printf(String s, double totalAmount) {
    }

    public static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }
    public static void printCase(String option) {
        System.out.println(GREEN + option + RESET);
    }

    public static void pressEnter() {
        readEnter("Press ENTER to continue...");
    }

}