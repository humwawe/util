package template.math;

/**
 * @author hum
 */
public class Prime {
    boolean isPrime(int x) {
        if (x < 2) {
            return false;
        }
        for (int i = 2; i <= x / i; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    // 埃氏筛法
    void getPrimes(int n) {
        int[] primes = new int[n + 1];
        boolean[] vis = new boolean[n + 1];
        int cnt = 0;
        for (int i = 2; i <= n; i++) {
            if (vis[i]) {
                continue;
            }
            primes[cnt++] = i;
            for (int j = i + i; j <= n; j += i) {
                vis[j] = true;
            }
        }
    }

    // 埃氏筛法优化，从x^2开始，将x^2,(x+1)*x,..n/x*x标记
    void getPrimesQuick(int n) {
        int[] primes = new int[n + 1];
        boolean[] vis = new boolean[n + 1];
        int cnt = 0;
        for (int i = 2; i <= n; i++) {
            if (vis[i]) {
                continue;
            }
            primes[cnt++] = i;
            for (int j = i; j <= n / i; j++) {
                vis[i * j] = true;
            }
        }
    }

    //线性筛法，每个合数被最小的质因子筛掉
    void getPrimes2(int n) {
        int[] primes = new int[n + 1];
        boolean[] vis = new boolean[n + 1];
        int cnt = 0;
        for (int i = 2; i <= n; i++) {
            if (!vis[i]) {
                primes[cnt++] = i;
            }
            for (int j = 0; primes[j] <= n / i; j++) {
                vis[primes[j] * i] = true;
                if (i % primes[j] == 0) {
                    break;
                }
            }
        }
    }

    // 素数分解
    void divide(int x) {
        for (int i = 2; i <= x / i; i++) {
            if (x % i == 0) {
                // 个数
                int s = 0;
                while (x % i == 0) {
                    x /= i;
                    s++;
                }
                System.out.println(i + ":" + s);
            }
        }
        if (x > 1) {
            System.out.println(x + ":" + 1);
        }
    }

}
