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
        System.out.println(ropes.get(num - 1).charAt(ropes.get(num - 1).root, index));
    }

    public void concat(int first, int second) {
        Node s1 = ropes.get(first - 1).getRoot();
        Node s2 = ropes.get(second - 1).getRoot();
        Rope rope = new Rope(s1.size + 1); // added space at the end
        rope.getRoot().left = s1;
        rope.getRoot().right = s2;

//        ropes.add(first - 1, rope);
        ropes.set(first - 1, rope);
        ropes.remove(second - 1);
    }

}
