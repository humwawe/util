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


  public final int mod = 1000000007;
  public final long m2 = (long) mod * mod;
  public final long BIG = 8L * m2;

  // A^e*v
  public int[] pow(int[][] A, int[] v, long e) {
    for (int i = 0; i < v.length; i++) {
      if (v[i] >= mod) {
        v[i] %= mod;
      }
    }
    int[][] MUL = A;
    for (; e > 0; e >>>= 1) {
      if ((e & 1) == 1) {
        v = mul(MUL, v);
      }
      MUL = p2(MUL);
    }
    return v;
  }

  // int matrix*int vector
  public int[] mul(int[][] A, int[] v) {
    int m = A.length;
    int n = v.length;
    int[] w = new int[m];
    for (int i = 0; i < m; i++) {
      long sum = 0;
      for (int k = 0; k < n; k++) {
        sum += (long) A[i][k] * v[k];
        if (sum >= BIG) {
          sum -= BIG;
        }
      }
      w[i] = (int) (sum % mod);
    }
    return w;
  }

  // int matrix^2 (be careful about negative value)
  public int[][] p2(int[][] A) {
    int n = A.length;
    int[][] C = new int[n][n];
    for (int i = 0; i < n; i++) {
      long[] sum = new long[n];
      for (int k = 0; k < n; k++) {
        for (int j = 0; j < n; j++) {
          sum[j] += (long) A[i][k] * A[k][j];
          if (sum[j] >= BIG) {
            sum[j] -= BIG;
          }
        }
      }
      for (int j = 0; j < n; j++) {
        C[i][j] = (int) (sum[j] % mod);
      }
    }
    return C;
  }


}
