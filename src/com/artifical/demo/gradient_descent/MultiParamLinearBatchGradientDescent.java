package com.artifical.demo.gradient_descent;

public class MultiParamLinearBatchGradientDescent {

    // 学习速度
    private double step_alpha = 0.0000001;
    // 精度
    private double precise = 0.000000002;

    private static int data_set_x[][] = new int[][] {
            {1, 1, 1, 1},
            {2104, 1416, 1534, 852}
    };

    private static int data_set_y[] = new int[] {460, 232, 315, 178};

    public static double parameters[] = new double[]{-35, -0.2};

    public double gradientDescent(int i) {

        int sum = 0;

        for (int m = 0; m < data_set_y.length; m ++) {
            sum += (hypothesis(m) - data_set_y[m]) * data_set_x[i][m];
        }

        double sub = step_alpha * sum / data_set_y.length;
        if (Math.abs(sub) < precise) {
            return 0;
        }

        return sub;
    }

    public double hypothesis (int m) {

        double result = 0;
        for (int i = 0; i < parameters.length; i ++) {
            result += parameters[i] * data_set_x[i][m];
        }

        return result;
    }

    public static void main(String[] args) {
        MultiParamLinearBatchGradientDescent singleParamLinearBatchGradientDescent = new MultiParamLinearBatchGradientDescent();

        double[] subArray = new double[data_set_y.length];
        while (true) {
            System.out.println(log());

            for (int index = 0; index < singleParamLinearBatchGradientDescent.parameters.length ; index ++) {
                subArray[index] = singleParamLinearBatchGradientDescent.gradientDescent(index);
            }


            boolean isEnd = true;
            for (int index = 0; index < subArray.length ; index ++) {
                if (subArray[index] != 0) {
                    isEnd = false;
                }
            }

            if (isEnd) {
                break;
            }

            for (int index = 0; index < subArray.length ; index ++) {
                if (subArray[index] != 0) {
                    singleParamLinearBatchGradientDescent.parameters[index] = singleParamLinearBatchGradientDescent.parameters[index] - subArray[index];
                }
            }
        }

        System.out.println(log());
    }

    private static String log() {
        StringBuffer sb = new StringBuffer("y = ");
        for (int index = 0; index < parameters.length ; index ++) {
            sb.append(parameters[index]).append(" * X").append(index);
            if (index < parameters.length - 1) {
                sb.append(" + ");
            }
        }
        return sb.toString();
    }
}
