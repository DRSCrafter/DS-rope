package com.company;

public class Rope {
    Node root;
    String sentence;

    Rope(String sentence) {
        this.sentence = sentence;
        root = new Node(middleCalc(sentence));
        populate(root, sentence);
    }

    Rope(int size) {
        root = new Node(size);
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

        else if (root instanceof LeafNode) { //TODO: fix this code smell
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
}
