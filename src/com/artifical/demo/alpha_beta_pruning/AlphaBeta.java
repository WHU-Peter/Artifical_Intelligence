package com.artifical.demo.alpha_beta_pruning;

import java.util.Random;
import java.util.Scanner;

public class AlphaBeta {

    private char[][] chess = new char[4][4];
    private char player;
    private char computer;

    /**
     * 初始化游戏界面：
     */
    public void StartGream() {
        for (int i = 1; i < 4; i++)
            for (int j = 1; j < 4; j++)
                chess[i][j] = '-';
        //首先让用户选择使用的符号：X/O,1为X,2为O
        System.out.println("欢迎您来到本游戏界面");
        System.out.println("请您选择您的游戏符号:X/O,输入数字1为X,数字2为O！");

        Scanner in = new Scanner(System.in);
        int a = in.nextInt();

        if (a == 1) {

            player = 'X';
            computer = 'O';

        } else if (a == 2) {

            player = 'O';
            computer = 'X';
        } else {

            player = 'X';
            computer = 'O';

            System.out.println("输入错误字符，默认玩家字符为X");

        }

        //开始选择谁先手，玩家先手，还是机器先手？
        System.out.println("请您选择是否先手？先手请按数字1，否则请按数字2！");
        Scanner in1 = new Scanner(System.in);
        int b = in1.nextInt();
        if (b == 1) {

            //玩家先手，画井字棋盘
            Print();

        } else if (b == 2) {
            //开局随便下
            Random rand = new Random();
            int row = rand.nextInt(3) + 1;
            int col = rand.nextInt(3) + 1;
            chess[row][col] = computer;
            Print();
        } else {

            System.out.println("输入错误字符，默认玩家先手！");
            Print();
        }

    }

    /**
     * 画棋盘
     */
    void Print() {
        System.out.println("**********");
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                System.out.print("| ");
                System.out.print(chess[i][j] + " ");
                if (j == 3) System.out.println("|");
            }
            if (i == 3) System.out.println("**********");
        }

    }

    /**
     * 判断赢的条件，有八种
     * 返回1赢，返回2输
     */
    public Boolean win(char player) {
        //：—|连线六种方法

        if (chess[1][1] == player && chess[1][2] == player && chess[1][3] == player) {
            return true;
        }
        if (chess[2][1] == player && chess[2][2] == player && chess[2][3] == player) {
            return true;
        }
        if (chess[3][1] == player && chess[3][2] == player && chess[3][3] == player) {
            return true;
        }
        if (chess[1][1] == player && chess[2][1] == player && chess[3][1] == player) {
            return true;
        }
        if (chess[1][2] == player && chess[2][2] == player && chess[3][2] == player) {
            return true;
        }
        if (chess[1][3] == player && chess[2][3] == player && chess[3][3] == player) {
            return true;
        }
        //斜着三点连一线俩种方法
        if (chess[1][1] == player && chess[2][2] == player && chess[3][3] == player) {
            return true;
        }
        if (chess[1][3] == player && chess[2][2] == player && chess[3][1] == player) {
            return true;
        }
        return false;

    }

    //判断棋盘是否还有地方能下棋子
    public boolean isEmpty() {//判断棋盘是否为空
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                if (chess[i][j] == '-') return false;
            }
        }
        return true;
    }

    //玩家开始下棋
    public void playerGo() {

        System.out.println("--------------------------：1，2，3");
        System.out.println("该您走啦，请您输入棋子位置：4，5，6");
        System.out.println("--------------------------：7，8，9");
        int row, col;
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        if (a >= 1 && a <= 9) {

            if (a % 3 == 0) row = a / 3;
            else row = a / 3 + 1;
            col = a - 3 * (row - 1);
            if (chess[row][col] != '-') {

                System.out.println("该位置已有棋子");
                playerGo();
            }

            chess[row][col] = player;
            Print();

        } else {

            System.out.println("您的字符输入错误，请您重新输入！");
            playerGo();

        }
    }

    //电脑开始下棋
    public void computerGo() throws InterruptedException {

        int best = 0;
        int bestScore = -1000;
        int score;
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (chess[i][j] == '-') {
                    chess[i][j] = computer;
                    score = bestInput("player", "computer", -1000, 1000);//alpha-beta剪枝是一个根据上下界限剪枝的算法，初始的上下界限为无穷
                    if (score >=bestScore) {//在同一层的节点里面需要不断试探性递归，用回溯法找到最合适的下棋点使自己胜算最大
                        bestScore = score;
                        best = (i - 1) * 3 + j;
                    }
                    chess[i][j] = '-';
                }
            }
        }
        int row, col;
        if (best % 3 == 0) row = best / 3;
        else row = best / 3 + 1;
        col = best - (row - 1) * 3;
        chess[row][col] = computer;
        Thread.sleep(2000);
        Print();


    }

    //剪枝算法（-1玩家赢，1电脑赢，0平局）
    int bestInput(String state, String nextState, int alpha, int beta) {//输入，调用剪枝的过程
        char ch;
        if (state.equals("computer")) {
            ch = computer;
        } else {
            ch = player;
        }

        // 有胜负或者是棋盘已经走满，该局属于叶子节点，可直接给出评分
        if (win(ch)) {
            if (state.equals("computer")) {
                //电脑赢
                return 1;
            } else {
                //玩家赢
                return -1;
            }
        } else if (isEmpty()) {

            //平局
            // System.out.println("平局！");
            return 0;
        } else {
            // 非叶子节点递归遍历子节点
            int score;
            for (int i = 1; i < 4; i++) {
                for (int j = 1; j < 4; j++) {
                    if (chess[i][j] == '-') {
                        chess[i][j] = ch;
                        score = bestInput(nextState, state, alpha, beta);
                        chess[i][j] = '-';
                        if (state.equals("computer")) {
                            if (score >= alpha) {
                                alpha = score;
                            }

                            // beta剪枝
                            if (alpha > beta) {
                                return beta;
                            }
                        } else {
                            if (score < beta) {
                                beta = score;
                            }
                            // alpha剪枝
                            if (beta <= alpha) {
                                return alpha;
                            }
                        }
                    }
                }
            }

            // 未剪枝
            if (state.equals("computer")) {
                return alpha;
            } else {
                return beta;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        AlphaBeta main = new AlphaBeta();
        main.StartGream();

        String current = "player";
        while (!main.win(main.computer) && !main.win(main.player) && !main.isEmpty()) {//终止条件是当前棋盘为空或者有一方胜利
            switch (current) {
                case "player":
                    main.playerGo();
                    current = "computer";
                    break;//当玩家下完后轮到电脑下
                case "computer":
                    main.computerGo();
                    current = "player";
                    break;
                default:
                    break;
            }
        }
        if (main.win(main.computer)) {
            System.out.println("电脑胜利！");
        } else if (main.win(main.player)) {
            System.out.println("玩家胜利");
        } else {
            System.out.println("平局！");
        }
    }
}
