package com.company;

import java.util.ArrayList;

public class Rope {
    private final Node root;

    Rope(String sentence) {
        root = new Node(middleCalc(sentence));
        populate(root, sentence);
    }

    Rope(int size) {
        root = new Node(size);
    }

    Rope(Node root){
        this.root = root;
    }

    public void populate(Node root, String str) {
        if (str.chars().filter(ch -> ch == ' ').count() == 1) { // if only two words remain then
            root.left = new LeafNode(str.substring(0, str.indexOf(' ')) + " ");
            root.right = new LeafNode(str.substring(str.indexOf(' ') + 1) + " ");

            root.size = str.indexOf(' ') + 1;

            return;
        } else if (str.chars().filter(ch -> ch == ' ').count() == 0) { // if only one word remains then
            root.left = new LeafNode(str + " ");

            root.size = str.length() + 1;

            return;
        }

        int middle = middleCalc(str);
        root.size = middle + 1; //increment for space at the end

        Node left = new Node();
        root.left = left;
        populate(left, str.substring(0, middle));

        Node right = new Node();
        root.right = right;
        populate(right, str.substring(middle + 1));
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

        if (root instanceof LeafNode) {
            stringBuilder.append(((LeafNode) root).getWord());
            return;
        }

        traversePreOrder(root.left, stringBuilder);
        traversePreOrder(root.right, stringBuilder);
    }

    public char charAt(Node root, int index) {
        int distance = index;

        System.out.println(root.size);

        if (root instanceof LeafNode)
            return ((LeafNode) root).word.charAt(distance);

        if (root.size <= index) {
            distance -= root.size;
            return charAt(root.right, distance);
        } else
            return charAt(root.left, distance);
    }

    public static int middleCalc(String sentence) {

        if (!sentence.contains(" "))
            return sentence.length() - 2;

        int middle = sentence.length() / 2;
        var before = sentence.lastIndexOf(' ', middle);
        var after = sentence.indexOf(' ', middle);

        if (before == -1 || (after != -1 && middle - before >= after - middle)) {
            middle = after;
        } else {
            middle = before;
        }

        return middle;
    }

    public Node split(int index){
        ArrayList<Node> words = split(root, index, new ArrayList<>() , false);
        Node root = new Node();
        concatNodes(root, words.toArray(new Node[0]));
        return  root;
    }

    private ArrayList<Node> split(Node root, int index, ArrayList<Node> newRope, boolean isDeletion){
        int distance = index;
        if (root == null)
            return newRope;
        if (isDeletion){
            newRope.add(root.right);
            root.right = null;
            return newRope;
        }
        if (root instanceof LeafNode){
            if (distance == 0){
                String right = ((LeafNode) root).word;
                newRope.add(new LeafNode(right));
                root = null;
            }
            else {
                String right = ((LeafNode) root).word.substring(index + 1);
                ((LeafNode) root).word = ((LeafNode) root).word.substring(0, index);
                newRope.add(new LeafNode(right));
            }
            isDeletion = true;
        }
        if (!isDeletion) {
            if (root.size <= index) {
                distance -= root.size;
                split(root.right, distance, newRope, false);
            } else
                split(root.left, distance, newRope, false);
        }
            return newRope;
        }

        private void concatNodes(Node root, Node[] words){
            if (words.length == 2){
                root.right = words[0];
                root.left = words[1];
                root.size = words[0].size;
                //todo add right subtree size if it doesn't null
                return;
            }
            if (words.length == 1){
                root.left = words[0];
                root.size = words[0].size;
                return;
            }

            int middle = words.length / 2;

            Node[] leftSubtree = new Node[middle];
            System.arraycopy(words, 0, leftSubtree, 0, middle);
            concatNodes(root.left, leftSubtree);

            Node[] rightSubtree = new Node[words.length - middle];
            System.arraycopy(words, middle, rightSubtree, 0, middle);
            concatNodes(root.right, rightSubtree);
        }

    public int SizeOfSentence(){
        int size = root.size;
        if (root.right != null)
            size += root.right.size;
        return size;
    }
}
