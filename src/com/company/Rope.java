package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Rope {
    private final Node root;

    Rope(String sentence) {
        root = new Node();

        String[] words = sentence.split(" ");

        populate(root, words);
    }

    Rope(int size) {
        root = new Node(size);
    }

    Rope(Node root) {
        this.root = root;
    }

    public void populate(Node root, String[] words) {
        if (words.length == 1){
            root.makeLeafNode(words[0] + " ");

            return;
        }

        if (words.length == 2){
            root.left = new Node(words[0] + " ");
            root.right = new Node(words[1] + " ");

            root.size = words[0].length() + 1;

            return;
        }

        root.size = sizeCalc(Arrays.copyOfRange(words, 0, (words.length + 1) / 2));

        Node left = new Node();
        root.left = left;
        populate(left, Arrays.copyOfRange(words, 0, (words.length + 1) / 2));

        Node right = new Node();
        root.right = right;
        populate(right, Arrays.copyOfRange(words, (words.length + 1) / 2, words.length));
    }

    public String report() {
        StringBuilder stringBuilder = new StringBuilder();
        traversePreOrder(root, stringBuilder);

        return stringBuilder.toString();
    }

    public Node getRoot() {
        return root;
    }

    private void traversePreOrder(Node root, StringBuilder stringBuilder) {
        if (root == null)
            return;

        if (root.isLeafNode) {
            stringBuilder.append(root.value);
            return;
        }

        traversePreOrder(root.left, stringBuilder);
        traversePreOrder(root.right, stringBuilder);
    }

    public char charAt(Node root, int index) {
        int distance = index;

        System.out.println(root.size);

        if (root.isLeafNode)
            return (root.value.charAt(distance));

        if (root.size <= index) {
            distance -= root.size;
            return charAt(root.right, distance);
        } else
            return charAt(root.left, distance);
    }

    public static int sizeCalc(String[] words) {
        int middle = 0;

        for (int i = 0; i < words.length; i++){
            middle += words[i].length() + 1;
        }

        return middle;
    }

    public Node split(int index) {
        ArrayList<Node> words = new ArrayList<>();
        split(root, index + 1, words);
        Node root2 = new Node();
        concatNodes(root2, words.toArray(new Node[0]));
        return root2;
    }

    private void split(Node root, int index, ArrayList<Node> newRope) {
        if (root.isLeafNode) {
            String right = root.value.substring(index);
            root.value = root.value.substring(0, index);
            newRope.add(new Node(right));
            return;
        }
        if (root.size <= index)
            split(root.right, index - root.size, newRope);
        else {
            split(root.left, index, newRope);
            newRope.add(root.right);
            root.right = null;
        }
    }

    private void concatNodes(Node root, Node[] words) {
        if (words.length == 2) {
            root.left = words[0];
            root.right = words[1];
            root.size = words[0].size;
            //todo add right subtree size if it doesn't null
            return;
        }
        if (words.length == 1) {
            root.left = words[0];
            root.size = words[0].size;
            return;
        }
        int middle = words.length / 2;

        Node[] leftSubtree = new Node[middle];
        System.arraycopy(words, 0, leftSubtree, 0, middle);
        root.left = new Node();
        concatNodes(root.left, leftSubtree);

        Node[] rightSubtree = new Node[words.length - middle];
        System.arraycopy(words, middle, rightSubtree, 0, words.length - middle);
        root.right = new Node();
        concatNodes(root.right, rightSubtree);
    }

    public void middleInsertion(String str, int index) {
        middleInsertion(root, index, str);
    }

    public void middleInsertion(Node root, int index, String str) {
        if (root == null)
            return;

        if (root.isLeafNode) {
            str = str.substring(0, str.length() - 1);

            if (index == 0) {
                String temp = root.value;
                root = new Node(str.length());
                root.left = new Node(str);
                root.right = new Node(temp);
            } else if (index == root.value.length() - 1) {
                String temp = root.value;
                root = new Node(temp.length());
                root.left = new Node(temp);
                root.right = new Node(str);
            } else {
                String left = root.value.substring(0, index + 1);
                String right = root.value.substring(index + 1);

                root = new Node(left.length());
                root.left = new Node(left);

                root.right = new Node(str.length());
                root.right.left = new Node(str);
                root.right.right = new Node(right);
            }

            return;
        }

        if (root.size <= index) {
            middleInsertion(root.right, index - root.size, str);
        } else
            middleInsertion(root.left, index, str);
    }

    public int SizeOfSentence() {
        int size = root.size;
        if (root.right != null)
            size += root.right.size;
        return size;
    }
}
