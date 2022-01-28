package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Rope {
    final Node root;

    Rope(String sentence) {
        root = new Node();
        String[] words = sentence.split(" ");
        for (int i = 0; i < words.length - 1; i++)
            words[i] = words[i].concat(" ");

        populate(root, words);
    }

    Rope(int size) {
        root = new Node(size);
    }

    Rope(Node root) {
        this.root = root;
    }

    public void populate(Node root, String[] words) {
        if (words.length == 1) {
            root.makeLeafNode(words[0]);
            return;
        }

        if (words.length == 2) {
            root.left = new Node(words[0]);
            root.right = new Node(words[1]);
            root.size = words[0].length();
            return;
        }
        String[] leftSubtree = Arrays.copyOfRange(words, 0, (words.length + 1) / 2);
        root.size = sizeCalc(leftSubtree);

        Node left = new Node();
        root.left = left;
        populate(left, leftSubtree);

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
        System.out.println(root.size);

        if (root.isLeafNode)
            return (root.value.charAt(index));

        if (root.size <= index)
            return charAt(root.right, index - root.size);
        else
            return charAt(root.left, index);
    }

    public static int sizeCalc(String[] words) {
        int middle = 0;

        for (int i = 0; i < words.length; i++) {
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

    public void middleInsertion(Node node, int index) {
        middleInsertion(root, index, node);
    }

    public void middleInsertion(Node root, int index, Node node) {

        if (root.isLeafNode) {
            if (index == 0) {
                String temp = root.value;
                root.makeAddressingNode(node.size, node, new Node(temp));
            } else if (index == root.value.length() - 1) {
                String temp = root.value.substring(0, root.value.length() - 1);
                root.makeAddressingNode(temp.length(), new Node(temp), node);
            } else {
                String left = root.value.substring(0, index);
                String right = root.value.substring(index);

                root.makeAddressingNode(left.length());
                root.left = new Node(left);

                root.right = new Node(node.size);
                root.right.left = node;
                root.right.right = new Node(right);
            }
            return;
        }

        if (root.size <= index) {
            middleInsertion(root.right, index - root.size, node);
            root.size = root.size + node.size;
        } else
            middleInsertion(root.left, index, node);
    }

    public Rope middleDeletion(int begin, int end) {
        ArrayList<Node> words = new ArrayList<>();
        split(root, begin, words);
        Node root2 = new Node();
        concatNodes(root2, words.toArray(new Node[0]));
        words.clear();
        split(root2, end - begin, words);
        Node root3 = new Node();
        concatNodes(root3, words.toArray(new Node[0]));

        return concat(root, root3);
    }

    private Rope concat(Node root1, Node root2) {
        Rope rope = new Rope(this.SizeOfSentence());
        rope.getRoot().left = root1;
        rope.getRoot().right = root2;
        return rope;
    }

    public int SizeOfSentence() {
        int size = root.size;
        if (root.right != null)
            size += root.right.size;
        return size;
    }
}
