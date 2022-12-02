package template.core;

import java.util.Arrays;

/**
 * @author hum
 */
public class MathMeth {
  public static long gcd(long a, long b) {
    return b != 0 ? gcd(b, a % b) : a;
  }


  // 求x, y，使得ax + by = gcd(a, b)，返回 x,y,g
  public static long[] exgcd(long a, long b) {
    long[] xyg = new long[3];
    xyg[2] = exgcd0(a, b, xyg);
    return xyg;
  }

  private static long exgcd0(long a, long b, long[] xy) {
    if (b == 0) {
      xy[0] = 1;
      xy[1] = 0;
      return a;
    }
    long g = exgcd0(b, a % b, xy);
    long tmp = xy[0];
    xy[0] = xy[1];
    xy[1] = tmp - a / b * xy[1];
    return g;
  }

  public static long lcm(long a, long b) {
    return a / gcd(a, b) * b;
  }

  // n以内的素数列表
  public static int[] sieveEratosthenes(int n) {
    if (n <= 32) {
      int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
      for (int i = 0; i < primes.length; i++) {
        if (n < primes[i]) {
          return Arrays.copyOf(primes, i);
        }
      }
      return primes;
    }

    int u = n + 32;
    double lu = Math.log(u);
    int[] ret = new int[(int) (u / lu + u / lu / lu * 1.5)];
    ret[0] = 2;
    int pos = 1;

    int[] isnp = new int[(n + 1) / 32 / 2 + 1];
    int sup = (n + 1) / 32 / 2 + 1;

    int[] tprimes = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
    for (int tp : tprimes) {
      ret[pos++] = tp;
      int[] ptn = new int[tp];
      for (int i = (tp - 3) / 2; i < tp << 5; i += tp) {
        ptn[i >> 5] |= 1 << (i & 31);
      }
      for (int j = 0; j < sup; j += tp) {
        for (int i = 0; i < tp && i + j < sup; i++) {
          isnp[j + i] |= ptn[i];
        }
      }
    }

    // 3,5,7
    // 2x+3=n
    int[] magic = {0, 1, 23, 2, 29, 24, 19, 3, 30, 27, 25, 11, 20, 8, 4, 13, 31, 22, 28, 18, 26, 10, 7, 12, 21, 17, 9, 6, 16, 5, 15, 14};
    int h = n / 2;
    for (int i = 0; i < sup; i++) {
      for (int j = ~isnp[i]; j != 0; j &= j - 1) {
        int pp = i << 5 | magic[(j & -j) * 0x076be629 >>> 27];
        int p = 2 * pp + 3;
        if (p > n) {
          break;
        }
        ret[pos++] = p;
        if ((long) p * p > n) {
          continue;
        }
        for (int q = (p * p - 3) / 2; q <= h; q += p) {
          isnp[q >> 5] |= 1 << q;
        }
      }
    }

    return Arrays.copyOf(ret, pos);
  }

  public static boolean isPrime(int x) {
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

  // x素数分解，返回素数列表ret[0]和次数ret[1]
  public static long[][] divide(long x) {
    int len = 0;
    int N = 65;
    long[] p = new long[N];
    long[] t = new long[N];
    for (long i = 2; i * i <= x; i++) {
      if (x % i == 0) {
        p[len] = i;
        t[len] = 0;
        while (x % i == 0) {
          t[len]++;
          x /= i;
        }
        len++;
      }
    }
    if (x > 1) {
      p[len] = x;
      t[len] = 1;
      len++;
    }
    return new long[][]{Arrays.copyOf(p, len), Arrays.copyOf(t, len)};
  }

  // int[] lpf = sieveLowestPrime(N);
  // factorFast(x, lpf)
  // 返回素数列表和次数分别存在 ret[i][0] ret[i][1]
  public static int[][] mulDivide(int n, int[] lpf) {
    int[][] f = new int[9][];
    int q = 0;
    while (lpf[n] > 0) {
      int p = lpf[n];
      if (q == 0 || p != f[q - 1][0]) {
        f[q++] = new int[]{p, 1};
      } else {
        f[q - 1][1]++;
      }
      n /= p;
    }
    return Arrays.copyOf(f, q);
  }

  // lpf[i]每个数的最小质因子
  public static int[] sieveLowestPrime(int n) {
    int tot = 0;
    int[] lpf = new int[n + 1];
    int u = n + 32;
    double lu = Math.log(u);
    int[] primes = new int[(int) (u / lu + u / lu / lu * 1.5)];
    for (int i = 2; i <= n; i++) {
      lpf[i] = i;
    }
    for (int p = 2; p <= n; p++) {
      if (lpf[p] == p) {
        primes[tot++] = p;
      }
      int tmp;
      for (int i = 0; i < tot && primes[i] <= lpf[p] && (tmp = primes[i] * p) <= n; i++) {
        lpf[tmp] = primes[i];
      }
    }
    return lpf;
  }

}
