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

  // 求最小正数解x使得 a*x mod m = b
  // 答案ret[0] 通解为 ret[0] + z*ret[1]
  public static long[] linearEquation(long a, long b, long m) {
    long[] exgcd = exgcd(a, m);
    if (b % exgcd[2] != 0) {
      return new long[]{-1, exgcd[2]};
    }
    long rem = m / exgcd[2];
    return new long[]{(exgcd[0] % rem + rem) % rem, rem};
  }

  // 求x使得 (x mod m1) = a1 ... (x mod mn) = an
  // 答案ret[0] 通解为 ret[0] + z*ret[1]
  public static long[] chineseRemainder(long[] a, long[] m) {
    assert a.length == m.length;
    int n = a.length;
    long ms = 1;
    for (int i = 0; i < n; i++) {
      ms *= m[i];
    }

    long[] msr = new long[n];
    for (int i = 0; i < n; i++) {
      msr[i] = ms / m[i];
    }
    long[] t = new long[n];
    for (int i = 0; i < n; i++) {
      t[i] = linearEquation(msr[i], 1, m[i])[0];
    }

    long res = 0;
    for (int i = 0; i < n; i++) {
      // attention overflow
      res += a[i] * msr[i] * t[i];
      res %= ms;
    }
    return new long[]{res, ms};
  }


  public static long phi(long x) {
    // x is prime: return x-1
    long res = x;
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


  int[] eulersEratosthenes(int n) {
    int u = n + 32;
    double lu = Math.log(u);
    int[] primes = new int[(int) (u / lu + u / lu / lu * 1.5)];
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
    return euler;
  }

  // 不会溢出的情况使用
  public static long modPow0(long m, long k, long p) {
    m %= p;
    long res = 1;
    int x = 63 - Long.numberOfLeadingZeros(k);
    for (; x >= 0; x--) {
      res = res * res % p;
      if (k << 63 - x < 0) {
        res = res * m % p;
      }
    }
    return res;
  }


  public static long modPow1(long m, long k, long p) {
    long res = 1 % p, t = m;
    while (k > 0) {
      if ((k & 1) == 1) {
        res = mul(res, t, p);
      }
      t = mul(t, t, p);
      k >>= 1;
    }
    return res % p;
  }


  // 求a*b mod p 可以处理负数和溢出
  public static long mul(long a, long b, long p) {
    boolean positive = true;
    if (a < 0) {
      a = -a;
      positive = false;
    }
    if (b < 0) {
      b = -b;
      positive = !positive;
    }

    long ans = 0;
    while (b > 0) {
      if ((b & 1) == 1) {
        ans = (ans + a) % p;
      }
      a = (a << 1) % p;
      b = b >> 1;
    }

    if (!positive) {
      return (-ans + p) % p;
    }

    return ans;
  }

  // sk代表的数字很大：sk > phi(p)
  public static long modPow2(long m, String sk, long p, long phiP) {
    long k = modStr(sk, phiP) + phiP;
    return modPow0(m, k, p);

  }

  public static long modStr(String s, long p) {
    int n = s.length();
    long res = 0;
    for (int i = 0; i < n; i++) {
      // attention overflow
      res = (res * 10 + (s.charAt(i) - '0')) % p;
    }
    return res;
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

  public static long arithmeticSum(long a1, long d, long n) {
    return n * a1 + n * (n - 1) / 2 * d;
  }

  public static long geometricSum(long a1, long q, long n, int p) {
    if (q == 1) {
      return a1 * n % p;
    }
    long res = a1 * (1 - modPow0(q, n, p)) % p * inv((p + (1 - q) % p) % p, p) % p;
    return res < 0 ? res + p : res;
  }

  public static long inv(long a, long p) {
    long b = p;
    long u = 1, v = 0;
    while (b >= 1) {
      long t = a / b;
      a -= t * b;
      u -= t * v;
      if (a < 1) {
        return (v %= p) < 0 ? v + p : v;
      }
      t = b / a;
      b -= t * a;
      v -= t * u;
    }
    return (u %= p) < 0 ? u + p : u;
  }

}
