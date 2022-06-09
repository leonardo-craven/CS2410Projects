package com.example.treedrawing;

class TreeNode {
    public TreeNode(int startX, int startY, int length, int angle) {
        this.startX = startX;
        this.startY = startY;
        this.endX = startX + (int)(Math.cos(Math.toRadians(angle)) * length);
        this.endY = startY - (int)(Math.sin(Math.toRadians(angle)) * length);
    }

    TreeNode left;
    TreeNode right;
    int startX;
    int startY;
    int endX;
    int endY;

}
