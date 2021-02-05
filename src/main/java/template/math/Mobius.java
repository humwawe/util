package template.math;

/**
 * 对某个数N分解质因数，N=p1^c1*p2^c2*...*pm^cm
 * 如果有某个质因子个数大于1个则N为0
 * 如果质因子都为1，当m个数为偶数则为1，奇数为-1
 *
 * @author hum
 */
public class Mobius {

    // 埃氏筛法求mobius函数
    int[] Mobius(int n) {
        int[] mobius = new int[n];
        int[] pri = new int[n];
        boolean[] vis = new boolean[n];
        int cnt = 0;
        mobius[1] = 1;
        for (int i = 2; i < n; ++i) {
            if (!vis[i]) {
                pri[cnt++] = i;
                mobius[i] = -1;
            }
            for (int j = 0; pri[j] * i < n; ++j) {
                int t = pri[j] * i;
                vis[t] = true;
                if (i % pri[j] == 0) {
                    mobius[t] = 0;
                    break;
                }
                mobius[t] = mobius[i] * -1;
            }
        }
        return mobius;
    }

}
