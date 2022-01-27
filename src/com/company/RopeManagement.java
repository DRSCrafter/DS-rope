package com.company;

import java.util.ArrayList;

public class RopeManagement {
    public ArrayList<Rope> ropes;

    RopeManagement() {
        ropes = new ArrayList<>();
    }

    public void insert(String str) {
        ropes.add(new Rope(str));
    }

    public void report() {
        for (int i = 0; i < ropes.size(); i++) {
            System.out.println((i + 1) + "." + ropes.get(i).report());
        }
    }

    public void index(int num, int index) {
        System.out.println(ropes.get(num - 1).charAt(ropes.get(num - 1).getRoot(), index));
    }

    public void concat(int first, int second) {
        var rope1 = ropes.get(first - 1);
        var rope2 = ropes.get(second - 1);
        Rope rope = new Rope(rope1.SizeOfSentence() + 1); // added space at the end
        rope.getRoot().left = rope1.getRoot();
        rope.getRoot().right = rope2.getRoot();

        ropes.set(first - 1, rope);
        ropes.remove(second - 1);
    }

    public void split(int RopeNum, int index){
        Node root = ropes.get(RopeNum - 1).split(index);
        ropes.add(new Rope(root));
    }

    public void middleDeletion(int RopeNum, int begin, int end){
        ropes.set(RopeNum - 1, ropes.get(RopeNum - 1).middleDeletion(begin, end));
    }

    public void middleInsert(int first, int index, int second){
        ropes.get(first - 1).middleInsertion(ropes.get(second - 1).report(), index);
    }

}
