package template.rand;

import java.util.Random;

/**
 * 比较适合函数有连续性的情况
 *
 * @author hum
 */
public class SimulateAnneal {
    // 如果保留精度要求高，可以把threshold减小
    double threshold = 1e-4;
    // 从1e4到1e-4，0.99的衰减次数不超过2000次，0.999不超过20000次
    double reduce = 0.99;
    int T = 100;
    double res = 1e8;
    Random random = new Random();

    void simulateAnneal() {

        /**
         * 随机计算T次，可以考虑卡时的方式来遍历
         * long cur = System.currentTimeMillis();
         * long end = cur + 800; //800ms 内
         * for (; cur < end; cur = System.currentTimeMillis())
         */
        for (int i = 0; i < T; i++) {
            optimize();
        }
    }

    void optimize() {
        double cur = rand(0, 1e4);
        for (double t = 1e4; t > threshold; t *= reduce) {
            // 找当前点附近的一个状态
            double next = rand(cur - t, cur + t);
            double delt = cal(next) - cal(cur);
            // 如果结果够好，Math.exp(-delt / t) 大于1
            // 如果结果不够好，以一定的概率取到next
            // 有时候如果是取最大值，则Math.exp(delt / t) < rand(0, 1)
            if (Math.exp(-delt / t) > rand(0, 1)) {
                cur = next;
            }
        }
    }

    private double cal(double v) {
        // 根据实际情况计算，并更新res
        double tmp = v * v;
        res = Math.min(res, tmp);
        return tmp;
    }

    double rand(double l, double r) {
        return random.nextDouble() * (r - l) + l;
    }

}
