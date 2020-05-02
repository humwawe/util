package template.math;

/**
 * 欧拉函数，1到n中与n互斥的个数
 *
 * @author hum
 */
public class Euler {

    // 如果 N = p1^c1 * p2^c2 * ... *pk^ck
    // 个数： N * (1-1/p1) * (1-1/p2) * ... * (1-1/pk)
    int phi(int x) {
        int res = x;
        for (int i = 2; i <= x / i; i++) {
            if (x % i == 0) {
                res = res / i * (i - 1);
                while (x % i == 0) {
                    x /= i;
                }
            }
        }
        if (x > 1) {
            res = res / x * (x - 1);
        }

        return res;
    }


    void getEulers(int n) {
        // primes[]存储所有素数
        int[] primes = new int[n + 1];
        // 存储每个数的欧拉函数
        int[] euler = new int[n + 1];
        boolean[] st = new boolean[n + 1];
        int cnt = 0;
        euler[1] = 1;
        for (int i = 2; i <= n; i++) {
            if (!st[i]) {
                primes[cnt++] = i;
                euler[i] = i - 1;
            }
            for (int j = 0; primes[j] <= n / i; j++) {
                int t = primes[j] * i;
                st[t] = true;
                if (i % primes[j] == 0) {
                    euler[t] = euler[i] * primes[j];
                    break;
                }
                euler[t] = euler[i] * (primes[j] - 1);
            }
        }
    }
}
