package com.artifical.demo.a_star;

import java.util.ArrayList;
import java.util.List;

public class A_Star {

    public static final int[][] maps = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
    };
    // 四个方向
    public static final int[][] direct = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public static final int step_weight = 10;

    private ArrayList<Node> openList = new ArrayList<Node>();
    private ArrayList<Node> closeList = new ArrayList<Node>();

    public Node findPath(Node start, Node end) {
        if (null == start || null == end) {
            throw new IllegalArgumentException();
        }

        calcG(start);
        calcH(end, start);
        start.calF();
        openList.add(start);

        while (!openList.isEmpty()) {
            Node currentMinFValueNode = findMinFValueFromOpenList(openList);
            openList.remove(currentMinFValueNode);
            closeList.add(currentMinFValueNode);

            if (currentMinFValueNode.getX() == end.getX() && currentMinFValueNode.getY() == end.getY()) {
                return currentMinFValueNode;
            }

            List<Node> neighborNodeList = findNeighborNodes(currentMinFValueNode);

            for (Node node : neighborNodeList) {
                //当前节点的相邻界节点已经在开放链表中
                if (exists(openList, node)) {
                    int G = calcG(node);
                    //途径当前节点tempStart到达该节点node的距离G更小时，更新F
                    if (G < node.getG()) {
                        node.setPreNode(currentMinFValueNode);
                        node.setG(G);;
                        node.calF();
                    }
                } else {
                    node.setPreNode(currentMinFValueNode);
                    node.setG(calcG(node));
                    node.setH(calcH(end, node));
                    node.calF();
                    openList.add(node);
                }
            }
        }

        return null;
    }

    private int calcG(Node node) {
        int G = step_weight;
        int parentG = node.getPreNode() != null ? node.getPreNode().getG() : 0;
        return G + parentG;
    }

    private int calcH(Node end, Node node) {
        int step = Math.abs(node.getX() - end.getX()) + Math.abs(node.getY() - end.getY());
        return step * step;
    }

    private List<Node> findNeighborNodes(Node currentMinFValueNode) {
        ArrayList<Node> arrayList = new ArrayList<Node>();
        // 只考虑上下左右，不考虑斜对角
        for (int i = 0; i < 4; i++) {
            int newX = currentMinFValueNode.getX() + direct[i][0];
            int newY = currentMinFValueNode.getY() + direct[i][1];
            // 如果当前节点的相邻节点，可达（不是障碍位置） 且 不在闭合链表
            if (canReach(newX, newY) && !exists(closeList, newX, newY))
                arrayList.add(new Node(newX, newY));
        }

        return arrayList;
    }

    private boolean exists(ArrayList<Node> nodeList, Node n) {
        for (Node node : nodeList) {
            if ((node.getX() == n.getX()) && (node.getY() == n.getY())) {
                return true;
            }
        }
        return false;
    }

    private boolean exists(ArrayList<Node> nodeList, int x, int y) {
        for (Node node : nodeList) {
            if ((node.getX() == x) && (node.getY() == y)) {
                return true;
            }
        }
        return false;
    }

    private boolean canReach(int x, int y) {
        if (x >= 0 && x < maps.length && y >= 0 && y < maps[0].length) {
            return maps[x][y] == 0;
        }
        return false;
    }

    private Node findMinFValueFromOpenList(ArrayList<Node> openList) {
        Node result = openList.get(0);
        for (Node node : openList) {
            if (node.getF() < result.getF()) {
                result = node;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        A_Star a_star = new A_Star();
        Node start = new Node(5, 1);
        Node end = new Node(2, 4);

        Node endNode = a_star.findPath(start, end);

        List<Node> nodePath = new ArrayList<>();
        while (null != endNode){
            nodePath.add(endNode);

            endNode = endNode.getPreNode();
        }

        for (int i = nodePath.size() - 1; i >= 0; i --) {
            System.out.print("(" + nodePath.get(i).getX() + " , " + nodePath.get(i).getY() + ")");
        }
    }
}
