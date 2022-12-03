package template.math;

import template.core.MathMeth;

/**
 * @author hum
 */
public class Remainder {
  // 求x使得 a*x mod m = b
  // 答案ret[0] 通解为 ret[0] + z*ret[1]
  public long[] linearEquation(long a, long b, long m) {
    long[] exgcd = MathMeth.exgcd(a, m);
    if (b % exgcd[2] != 0) {
      return new long[]{-1, -1};
    }
    return new long[]{exgcd[0], m / exgcd[2]};
  }

  // 求x使得 (x mod m1) = a1 ... (x mod mn) = an
  // 答案ret[0] 通解为 ret[0] + z*ret[1]
  public long[] chineseRemainder(long[] a, long[] m) {
    //assert m 两两互质
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
      res += a[i] * msr[i] * t[i];
      res %= ms;
    }
    return new long[]{res, ms};
  }
}
