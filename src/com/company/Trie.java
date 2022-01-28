package com.company;

import java.io.File;
import java.util.LinkedList;
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

    public String[] findWords(String prefix){
        TrieNode node = lastNode(prefix);
        LinkedList<String> words = new LinkedList<>();
        if (node != null)
            suggestionWords(node, prefix, words);

        return words.toArray(new String[0]);
    }

    private void suggestionWords(TrieNode root, String prefix, LinkedList<String> words){
        if (root.isFullWord)
            words.add(prefix);
        for (int i = 0; i < 26; i++){
            if (root.children[i] != null)
                suggestionWords(root.children[i], prefix + root.children[i].value, words);
        }
    }
    private TrieNode lastNode(String prefix){
        TrieNode current = root;
        for (int i = 0; i < prefix.length(); i++){
            int index = prefix.charAt(i) - 97;
            TrieNode child = root.children[index];
            if (child == null)
                return null;
            current = child;
        }
        return current;
    }
}
