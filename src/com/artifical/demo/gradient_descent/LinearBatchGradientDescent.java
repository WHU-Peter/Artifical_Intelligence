package com.artifical.demo.gradient_descent;

public class LinearBatchGradientDescent {

    // 学习速度
    private double step_alpha = 0.0000000001;
    // 精度
    private double precise = 0.000002;

    private int data_set_x[] = new int[] {2104, 1416, 1534, 852};
    private int data_set_y[] = new int[] {460, 232, 315, 178};

    public double a;
    public double b;

    public boolean gradientDescentA() {
        int sum = 0;

        for (int i = 0; i < data_set_x.length; i ++) {
            sum += hypothesis(data_set_x[i]) - data_set_y[i];
        }

        double sub = step_alpha * sum / data_set_x.length;
        if (Math.abs(sub) < precise) {
            return false;
        }

        a = a - sub;
        return true;
    }

    public boolean gradientDescentB() {

        int sum = 0;

        for (int i = 0; i < data_set_x.length; i ++) {
            sum += (hypothesis(data_set_x[i]) - data_set_y[i]) * data_set_x[i];
        }

        double sub = step_alpha * sum / data_set_x.length;
        if (Math.abs(sub) < precise) {
            return false;
        }

        b = b - sub;
        return true;
    }

    public double hypothesis (int x) {
        return a + b * x;
    }

    public static void main(String[] args) {
        LinearBatchGradientDescent linearBatchGradientDescent = new LinearBatchGradientDescent();
        linearBatchGradientDescent.a = 100;
        linearBatchGradientDescent.b = 1000;

        while (true) {
            System.out.println("y = " + linearBatchGradientDescent.a + " + " + linearBatchGradientDescent.b + " * x");
            boolean aIsConvergence = linearBatchGradientDescent.gradientDescentA();
            boolean bIsaIsConvergence = linearBatchGradientDescent.gradientDescentB();
            if (
                    !aIsConvergence
                            &&
                                !bIsaIsConvergence
            ) {
                break;
            }
        }

        System.out.println("y = " + linearBatchGradientDescent.a + " + " + linearBatchGradientDescent.b + " * x");
    }
}
