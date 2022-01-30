package com.company;

import java.util.Locale;
import java.util.Scanner;
import java.util.Stack;

public class Driver {
    private static final String explanation = """
            In the name of GOD
            Trie-Rope data structure
                            
            Functions:
                status              Displays all of the ropes.
                new "[1]"           Inserts the [1] sentence into a new rope.
                index [1] [2]       Displays [1]th character of [2]th rope.
                concat [1] [2]      Appends [2]th rope to the end of [1]th rope.
                insert [1] [2] [3]  Adds [3]th rope to the [2]th index of [1]th rope.
                split [1] [2]       Splits [1]th rope into 2 separate ropes from [2]th index.
                delete [1] [2] [3]  Removes every character from [2]th to [3]th index in the [1]th rope.
                autocomplete [1]    Displays all the words in the trie that begin with [1] string.
                undo                Undoes the previous command.
                
            Please enter the text file address:""";

    public static void run() {
        Scanner scanner = new Scanner(System.in);

        RopeManagement ropeManagement = new RopeManagement();
        Stack<String> history = new Stack<>();

        System.out.println(explanation);
        Trie trie = new Trie(scanner.nextLine());

        while (true) {
            String input = scanner.nextLine();
            String command = input.trim(); //get rid of the possible spaces before and after the input

            String[] instruction = command.split(" ");

            switch (instruction[0].toLowerCase(Locale.ROOT)) {
                case "status" -> ropeManagement.report();
                case "new" -> {
                    ropeManagement.insert(command.substring(command.indexOf(" ") + 2, command.length() - 1)); // Selecting the content between double quotations
                    history.push(command);
                }
                case "index" -> System.out.println(ropeManagement.index(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2])));
                case "concat" -> {
                    int distance = ropeManagement.getRope(Integer.parseInt(instruction[2]) - 1).report().length();
                    ropeManagement.concat(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2]));
                    history.push(command + " " + (distance));
                }
                case "insert" -> {
                    ropeManagement.middleInsert(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2]), Integer.parseInt(instruction[3]));
                    history.push(command);
                }
                case "split" -> {
                    ropeManagement.split(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2]));
                    history.push(command);
                }
                case "delete" -> {
                    history.push(command + " " + ropeManagement.getRope(
                            Integer.parseInt(instruction[1]) - 1).report().substring(Integer.parseInt(instruction[2]), Integer.parseInt(instruction[3]) + 1));
                    ropeManagement.middleDeletion(Integer.parseInt(instruction[1]), Integer.parseInt(instruction[2]), Integer.parseInt(instruction[3]));
                }
                case "autocomplete" -> {
                    String[] words = trie.findWords(instruction[1]);
                    for (int i = 0; i < words.length; i++) {
                        if (words[i] != null)
                            System.out.println((i + 1) + "." + words[i]);
                    }
                    if (words.length != 0) {
                        int number = Integer.parseInt(scanner.nextLine());
                        trie.updateFrequency(words[number - 1]);
                        ropeManagement.insert(words[number - 1]);
                        history.push(command);
                    }
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
                case "undo" -> {
                    String[] prevInstr = history.pop().split(" ");

                    switch (prevInstr[0].toLowerCase(Locale.ROOT)) {
                        case "new", "autocomplete" -> ropeManagement.removeLastRope();
                        case "split" -> ropeManagement.concat(1, ropeManagement.ropes.size());
                        case "concat" -> ropeManagement.split(Integer.parseInt(prevInstr[1]), ropeManagement.getRope((Integer.parseInt(prevInstr[1]) - 1)).report().length() - Integer.parseInt(prevInstr[3]));
                        case "insert" -> ropeManagement.middleDeletion(Integer.parseInt(prevInstr[1]), Integer.parseInt(prevInstr[2]) + 1,
                                Integer.parseInt(prevInstr[2]) + ropeManagement.getRope(Integer.parseInt(prevInstr[3]) - 1).report().length()+1);
                        case "delete" -> ropeManagement.middleInsert(Integer.parseInt(prevInstr[1]),
                                Integer.parseInt(prevInstr[2]) - 1, new Rope(prevInstr[4]));
                    }
                }
                default -> System.out.println("Invalid instruction!");
            }
        }
    }
}
