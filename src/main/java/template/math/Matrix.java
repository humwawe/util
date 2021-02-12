package template.math;

/**
 * @author hum
 */
public class Matrix {
    int n = 5;
    int[][] a = new int[n][n];
    int[][] b = new int[n][n];

    // 矩阵乘法，结果对p取模
    int[][] mul(int[][] a, int[][] b, int p) {
        int[][] c = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    c[i][j] = (int) ((c[i][j] + (long) a[i][k] * b[k][j]) % p);
                }
            }
        }
        return c;
    }

    // 快速幂
    int[][] qp(int[][] a, int k, int p) {
        int[][] c = new int[n][n];
        for (int i = 0; i < n; i++) {
            c[i][i] = 1;
        }
        while (k > 0) {
            if ((k & 1) == 1) {
                c = mul(c, a, p);
            }
            k >>= 1;
            a = mul(a, a, p);
        }
        return c;
    }

    // 求 a^1 + a^2 + a^3 + a^4 + .... + a^k
    // n为偶数：sum(n) = (a + a^(k/2))*sum(n/2)
    // 奇数：sum(n) = sum(n-1) + a^k，或者 a + a*sum(n-1) (可以少一次快速幂)
    int[][] sum(int[][] a, int k, int p) {
        if (k == 1) {
            return a;
        }
        int[][] c = new int[n][n];
        for (int i = 0; i < n; i++) {
            c[i][i] = 1;
        }
        c = add(c, qp(a, k >> 1, p), p);
        c = mul(c, sum(a, k >> 1, p), p);
        if ((k & 1) == 1) {
            c = add(c, qp(a, k, p), p);
        }
        return c;
    }

    // 矩阵加法
    int[][] add(int[][] a, int[][] b, int p) {
        int[][] c = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j] = (a[i][j] + b[i][j]) % p;
            }
        }
        return c;
    }

}
