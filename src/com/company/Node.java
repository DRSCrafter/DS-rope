package com.company;

public class Node {
    public int size;
    public Node left;
    public Node right;
    public boolean isLeafNode;
    public String value;

    Node() {
        this.isLeafNode = false;
    }

    Node(String value) {
        this.value = value;
        this.isLeafNode = true;
    }

    Node(int size) {
        this.size = size;
        this.isLeafNode = false;
    }

    public void makeLeafNode(String value) {
        this.value = value;
        isLeafNode = true;
        left = null;
        right = null;
    }

    public void makeAddressingNode(int size) {
        this.value = null;
        this.size = size;
        this.isLeafNode = false;
    }

    public void makeAddressingNode(int size, Node left, Node right) {
        this.value = null;
        this.size = size;
        this.isLeafNode = false;
        this.left = left;
        this.right = right;
    }
}
