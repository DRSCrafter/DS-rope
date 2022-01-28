package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Trie {

    Trie(String address) {
        try {
            Scanner scanner = new Scanner(new File(address));

            while (scanner.hasNext())
                this.insert(scanner.nextLine());

        } catch (FileNotFoundException e) {
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

    private final TrieNode root = new TrieNode(' ');

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

    public String[] findWords(String str) {
        TrieNode node = lastNode(str);
        LinkedList<String> words = new LinkedList<>();

        if (node != null)
            suggestionWords(node, str, words);

        return words.toArray(new String[0]);
    }

    private void suggestionWords(TrieNode root, String str, LinkedList<String> words) {
        if (root.isFullWord)
            words.add(str);

        for (int i = 0; i < 26; i++) {
            if (root.children[i] != null)
                suggestionWords(root.children[i], str + root.children[i].value, words);
        }
    }

    private TrieNode lastNode(String str) {
        TrieNode current = root;
        for (int i = 0; i < str.length(); i++) {
            int index = str.charAt(i) - 97;
            TrieNode child = root.children[index];
            if (child == null)
                return null;
            current = child;
        }
        return current;
    }
}
