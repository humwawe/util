package template.math;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hum
 */
public class Bsgs {
  // baby step giant step 算法求 a^x mod p = b 的x最小非负整数解（a,p互质）
  // 令 x=i*t-j 其中t=sqrt(p) 0<=j<=t-1，原式等价于 (a^t)^i mod p = b*a^j
  // 记录 b*a^j mod p 放入hash表
  // 遍历i的值，计算(a^t)^i mod p 的值是否在hash表中
  int bsgs(int a, int b, int p) {
    b %= p;
    int t = (int) (Math.sqrt(p)) + 1;
    if (b == 1) {
      return 0;
    }
    Map<Long, Integer> map = new HashMap<>();
    long val = b;
    for (int j = 0; j < t; j++) {
      map.put(val, j);
      // b*a^j
      val = (long) val * a % p;

    }
    // a^t
    a = qp(a, t, p);
    if (a == 0) {
      return b == 0 ? 1 : -1;
    }
    val = 1;
    for (int i = 1; i <= t; i++) {
      // (a^t)^i
      val = val * a % p;
      int j = map.getOrDefault(val, -1);
      if (j >= 0 && i * t - j >= 0) {
        return i * t - j;
      }
    }
    return -1;
  }

  int x, y;

  // 求 a^x mod p = b 的x最小非负整数解 (a p 可以不互质)
  // 使用 exgcd 求逆元
  // 用最大公约数化简，直到可以用bsgs求
  int exBsgs(int a, int b, int p) {
    x = 0;
    y = 0;

    int d = 0, A = 1, k = 0;
    if (b == 1) {
      return 0;
    }
    while ((d = gcd(a, p)) > 1) {
      if (b % d != 0) {
        return -1;
      }
      b /= d;
      p /= d;
      k++;
      A = (int) ((long) A * (a / d) % p);
      if (A == b) {
        return k;
      }
    }
    exgcd(A, p);
    x = (x % p + p) % p;
    int res = bsgs(a, (int) ((long) b * x % p), p);
    return res == -1 ? -1 : res + k;
  }

  int gcd(int a, int b) {
    return b != 0 ? gcd(b, a % b) : a;
  }


  int exgcd(int a, int b) {
    if (b == 0) {
      x = 1;
      y = 0;
      return a;
    }
    int d = exgcd(b, a % b);
    int tmp = x;
    x = y;
    y = tmp - a / b * y;
    return d;
  }

  int qp(int m, int k, int p) {
    long res = 1 % p, t = m;
    while (k > 0) {
      if ((k & 1) == 1) {
        res = res * t % p;
      }
      t = t * t % p;
      k >>= 1;
    }
    return (int) res % p;
  }

  // from uwi
  public long bsgs(long alpha, long beta, int p) {
    int m = (int) Math.sqrt(p) + 1;
    long[] table = new long[m];
    long val = 1;
    for (int j = 0; j < m; j++) {
      table[j] = val << 32 | j;
      val = val * alpha % p;
    }
    Arrays.sort(table);
    long ainvm = invl(val, p);

    long g = beta;
    for (int i = 0; i < m; i++) {
      int ind = Arrays.binarySearch(table, g << 32);
      if (ind < 0) {
        ind = -ind - 1;
      }
      if (ind < m && table[ind] >>> 32 == g) {
        return i * m + (int) table[ind];
      }
      g = g * ainvm % p;
    }
    return -1;
  }


  public long invl(long a, long mod) {
    long b = mod;
    long p = 1, q = 0;
    while (b > 0) {
      long c = a / b;
      long d;
      d = a;
      a = b;
      b = d % b;
      d = p;
      p = q;
      q = d - c * q;
    }
    return p < 0 ? p + mod : p;
  }
}
