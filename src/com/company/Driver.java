package com.company;

import java.util.Locale;
import java.util.Scanner;

public class Driver {
    public static void run() {
        RopeManagement ropeManagement = new RopeManagement();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            String command = input.trim(); //get rid of the possible spaces before and after the input

            String[] instruction = command.split(" ");

            switch (instruction[0].toLowerCase(Locale.ROOT)) {
                case "status":
                    ropeManagement.report();
                    break;
                case "new":
                    ropeManagement.insert(command.substring(command.indexOf(" ") + 2, command.length() - 1)); // Selecting the content between double quotations
                    break;
                case "index":
                    System.out.println(ropeManagement.index(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2])));
                    break;
                case "concat":
                    ropeManagement.concat(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2]));
                    break;
                case "insert":
                    ropeManagement.middleInsert(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2]), Integer.parseInt(instruction[3]));
                    break;
                case "split":
                    ropeManagement.split(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2]));
                    break;
                case "delete":
                    ropeManagement.middleDeletion(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2]), Integer.parseInt(instruction[3]));
                    break;
                case "autocomplete":
                    // Not implemented yet -_-
                    break;
                case "exit":
                    System.out.println("Thanks!");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid instruction");
            }
        }
    }
}
