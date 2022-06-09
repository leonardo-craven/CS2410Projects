package com.example.treedrawing;

import java.util.ArrayList;

public class Tree {
    public Tree(int startX, int startY, int maxLength, int maxAngle) {
        this.maxLength = maxLength;
        this.maxAngle = maxAngle;
        root = generateTree(startX, startY, 100, 90, 0);
    }

    private TreeNode generateTree(int startX, int startY,
                              int length, int angle, int depth) {
        if (depth == MAX_DEPTH) return null;
        TreeNode node = new TreeNode(startX, startY, length, angle);

        int leftLength = (int)(Math.random() * maxLength);
        int leftAngle = 90 + (int)(Math.random() * maxAngle);
        node.left = generateTree(node.endX, node.endY, leftLength, leftAngle, depth + 1);

        int rightLength = (int)(Math.random() * maxLength);
        int rightAngle = 90 - (int)(Math.random() * maxAngle);
        node.right = generateTree(node.endX, node.endY, rightLength, rightAngle, depth + 1);
        return node;
    }



    private TreeNode root;

    public TreeNode getRoot() {
        return root;
    }

    private int maxLength;
    private int maxAngle;
    private int MAX_DEPTH = 5;

}
