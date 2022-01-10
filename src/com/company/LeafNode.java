package com.company;

public class LeafNode extends Node {
    String word;

    public String getWord() {
        return word;
    }

    LeafNode(String word) {
        super(word.length());
        this.word = word;
    }
}
