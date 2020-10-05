package com.artifical.demo.gradient_descent;

public class SingleParamLinearBatchGradientDescent {

    // 学习速度
    private double step_alpha = 0.0000001;
    // 精度
    private double precise = 0.000000002;

    private int data_set_x[] = new int[] {2104, 1416, 1534, 852};
    private int data_set_y[] = new int[] {460, 232, 315, 178};

    public double a;
    public double b;

    public double gradientDescentA() {
        int sum = 0;

        for (int i = 0; i < data_set_x.length; i ++) {
            sum += hypothesis(data_set_x[i]) - data_set_y[i];
        }

        double sub = step_alpha * sum / data_set_x.length;
        if (Math.abs(sub) < precise) {
            return 0;
        }

        return sub;
    }

    public double gradientDescentB() {

        int sum = 0;

        for (int i = 0; i < data_set_x.length; i ++) {
            sum += (hypothesis(data_set_x[i]) - data_set_y[i]) * data_set_x[i];
        }

        double sub = step_alpha * sum / data_set_x.length;
        if (Math.abs(sub) < precise) {
            return 0;
        }

        return sub;
    }

    public double hypothesis (int x) {
        return a + b * x;
    }

    public static void main(String[] args) {
        SingleParamLinearBatchGradientDescent singleParamLinearBatchGradientDescent = new SingleParamLinearBatchGradientDescent();
        singleParamLinearBatchGradientDescent.a = -40;
        singleParamLinearBatchGradientDescent.b = -0.2;

        while (true) {
            System.out.println("y = " + singleParamLinearBatchGradientDescent.a + " + " + singleParamLinearBatchGradientDescent.b + " * x");
            double aSub = singleParamLinearBatchGradientDescent.gradientDescentA();
            double bSub = singleParamLinearBatchGradientDescent.gradientDescentB();

            if (
                    aSub == 0
                            &&
                            bSub == 0
            ) {
                break;
            }

            singleParamLinearBatchGradientDescent.a = singleParamLinearBatchGradientDescent.a - aSub;
            singleParamLinearBatchGradientDescent.b = singleParamLinearBatchGradientDescent.b - bSub;
        }

        System.out.println("y = " + singleParamLinearBatchGradientDescent.a + " + " + singleParamLinearBatchGradientDescent.b + " * x");
    }
}
