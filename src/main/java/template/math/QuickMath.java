package template.math;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hum
 */
public class QuickMath {
  // 求 m^k mod p，时间复杂度 O(logk)
  int qp(int m, int k, int p) {
    long res = 1 % p, t = m;
    while (k > 0) {
      if ((k & 1) == 1) {
        // 若res,t,p都是同一量级，比如int或long，则直接算可能越界
        // 可以使用在mod下的乘积 res = qm(res, t, p)
        res = res * t % p;
      }
      // t = qm(t, t, p)
      t = t * t % p;
      k >>= 1;
    }
    return (int) res % p;
  }


  // 求 a*b mod p
  long qm(long a, long b, long p) {
    long ans = 0;
    while (b > 0) {
      if ((b & 1) == 1) {
        ans = (ans + a) % p;
      }
      a = (a << 1) % p;
      b = b >> 1;
    }
    return ans;
  }


  // 求a*b mod p 可以处理负数情况
  long qm2(long a, long b, long p) {
    boolean positive = true;
    if (a < 0) {
      a = -a;
      positive = false;
    }
    if (b < 0) {
      b = -b;
      positive = !positive;
    }

    long ans = qm(a, b, p);
    if (!positive) {
      return (-ans + p) % p;
    }

    return ans;
  }

  // baby step giant step 算法求 a^x mod p = b 的x最小非负整数解
  // 令 x=i*t-j 其中t=sqrt(p) 0<=j<=t-1，原式等价于 (a^t)^i mod p = b*a^j
  // 记录 b*a^j mod p 放入hash表
  // 遍历i的值，计算(a^t)^i mod p 的值是否在hash表中
  int bsgs(int a, int b, int p) {
    Map<Integer, Integer> map = new HashMap<>();
    b %= p;
    int t = (int) (Math.sqrt(p)) + 1;
    for (int j = 0; j < t; j++) {
      // b*a^j
      int val = (int) ((long) b * qp(a, j, p) % p);
      map.put(val, j);
    }
    // a^t
    a = qp(a, t, p);
    if (a == 0) {
      return b == 0 ? 1 : -1;
    }
    for (int i = 0; i <= t; i++) {
      // (a^t)^i
      int val = qp(a, i, p);
      int j = map.getOrDefault(val, -1);
      if (j >= 0 && i * t - j >= 0) {
        return i * t - j;
      }
    }
    return -1;
  }

}
