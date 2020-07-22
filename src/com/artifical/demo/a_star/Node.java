package com.artifical.demo.a_star;

public class Node {

    //节点坐标
    private int x;
    private int y;

    // 预计消耗路径长度 （F = G + H）
    private int F;

    // 已经消耗路径长度
    private int G;

    // 启发值
    private int H;

    // 路径前置节点
    private Node preNode;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int calF() {
        this.F = this.G + this.H;
        return F;
    }

    public int getF() {
        return F;
    }

    public void setF(int f) {
        F = f;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    public int getH() {
        return H;
    }

    public void setH(int h) {
        H = h;
    }

    public Node getPreNode() {
        return preNode;
    }

    public void setPreNode(Node preNode) {
        this.preNode = preNode;
    }
}
