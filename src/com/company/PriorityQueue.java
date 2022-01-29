package com.company;

public class PriorityQueue {

    Node front;

    static class Node{
        String word;
        int priority;
        Node next;
        Node(String word, int priority){
            this.word = word;
            this.priority = priority;
        }
    }

    public void insert(String word, int freq) {
        Node node = new Node(word, freq);
        if (front == null || freq > front.priority) {
            node.next = front;
            front = node;
            return;
        }
        Node current = front;
        while (current.next != null && (current.next.priority > node.priority))
            current = current.next;
        node.next = current.next;
        current.next = node;
    }
    public String remove(){
        if (front == null)
            return null;
        Node temp = front;
        front = front.next;
        return temp.word;
    }
}
