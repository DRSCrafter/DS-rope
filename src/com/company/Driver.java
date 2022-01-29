package com.company;

import java.util.Locale;
import java.util.Scanner;

public class Driver {
    public static void run(String address) {
        Scanner scanner = new Scanner(System.in);

        RopeManagement ropeManagement = new RopeManagement();
        Trie trie = new Trie(address);

        while (true) {
            String input = scanner.nextLine();
            String command = input.trim(); //get rid of the possible spaces before and after the input

            String[] instruction = command.split(" ");

            switch (instruction[0].toLowerCase(Locale.ROOT)) {
                case "status" -> ropeManagement.report();
                case "new" -> ropeManagement.insert(command.substring(command.indexOf(" ") + 2, command.length() - 1)); // Selecting the content between double quotations
                case "index" -> System.out.println(ropeManagement.index(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2])));
                case "concat" -> ropeManagement.concat(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2]));
                case "insert" -> ropeManagement.middleInsert(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2]), Integer.parseInt(instruction[3]));
                case "split" -> ropeManagement.split(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2]));
                case "delete" -> ropeManagement.middleDeletion(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2]), Integer.parseInt(instruction[3]));
                case "autocomplete" -> {
                    String[] words = trie.findWords(command.substring(command.indexOf(" ") + 1));
                    for (int i = 0; i < words.length ; i++) {
                        if (words[i] != null)
                            System.out.println((i + 1) + "." + words[i]);
                    }
                    int number = Integer.parseInt(scanner.nextLine());
                    trie.updateFrequency(words[number - 1]);
                    ropeManagement.insert(words[number - 1]);
                }
                case "exit" -> {
                    System.out.println("Thanks!");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
                default -> throw new IllegalArgumentException("Invalid instruction");
            }
        }
    }
}
