package com.company;

import java.io.File;
import java.util.Scanner;

public class Trie {

    Trie(String address) {
        try {
            Scanner scanner = new Scanner(new File(address));

            while (scanner.hasNext()) {
                this.insert(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Invalid Address!");
        }
    }

    class TrieNode {
        char value;
        TrieNode[] children = new TrieNode[26]; // 26 is the number of alphabets (possibilities)
        boolean isFullWord;

        public TrieNode(char value) {
            this.value = value;
            this.isFullWord = false;
        }
    }

    private TrieNode root = new TrieNode(' ');

    private void insert(String str) {
        TrieNode current = root;
        for (int i = 0; i < str.length(); i++) {
            int index = str.charAt(i) - 97;

            if (current.children[index] == null)
                current.children[index] = new TrieNode(str.charAt(i));

            current = current.children[index];
        }
        current.isFullWord = true; // Confirm the placement of a word for further usages
    }
}
