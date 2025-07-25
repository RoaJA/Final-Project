package quickcall;

import java.util.Scanner;

public class InputValidator {

    public static String promptNonEmpty(Scanner sc, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = sc.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }

    public static int promptInt(Scanner sc, String prompt, int min, int max) {
        int val;
        while (true) {
            System.out.print(prompt);
            try {
                val = Integer.parseInt(sc.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.println("⚠ Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("⚠ Invalid number.");
            }
        }
    }

    public static double promptDouble(Scanner sc, String prompt, double min, double max) {
        double val;
        while (true) {
            System.out.print(prompt);
            try {
                val = Double.parseDouble(sc.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.println("⚠ Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("⚠ Invalid number.");
            }
        }
    }

    public static String promptPhone10(Scanner sc, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (input.matches("\\d{10}")) return input;
            System.out.println("⚠ Phone must be exactly 10 digits.");
        }
    }
}
