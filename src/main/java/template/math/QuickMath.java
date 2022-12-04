package template.math;

/**
 * @author hum
 */
public class QuickMath {
  // 求 m^k mod p，时间复杂度 O(logk)
  int qp(int m, int k, int p) {
    boolean f = false;
    long res = 1 % p, t = m;
    while (k > 0) {
      if ((k & 1) == 1) {
        // %p 如果被使用则说明 m^k 大于p
        // if (res * t >= p) {
        //   f = true;
        // }
        // 若res,t,p都是同一量级，比如int或long，则直接算可能越界
        // 可以使用在mod下的乘积 res = qm(res, t, p)
        res = res * t % p;
      }
      // if (t * t >= p) {
      //   f = true;
      // }
      // t = qm(t, t, p)
      t = t * t % p;
      k >>= 1;
    }
    return (int) res % p;
  }

  // 求 m^k p p
  long pow(long m, long k, long p) {
    //		m %= p;
    long ret = 1;
    int x = 63 - Long.numberOfLeadingZeros(k);
    for (; x >= 0; x--) {
      ret = ret * ret % p;
      if (k << 63 - x < 0) {
        ret = ret * m % p;
      }
    }
    return ret;
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


  // 求 (a*i+b)/m 的和（向下取整）， i范围[0,n-1] a,b≥0，n,m>0
  public long floorSum(long n, long m, long a, long b) {
    long ans = 0;
    if (a >= m) {
      ans += (n - 1) * n * (a / m) / 2;
      a %= m;
    }
    if (b >= m) {
      ans += n * (b / m);
      b %= m;
    }

    long yMax = (a * n + b) / m;
    long xMax = yMax * m - b;
    if (yMax == 0) {
      return ans;
    }
    ans += (n - (xMax + a - 1) / a) * yMax;
    ans += floorSum(yMax, a, m, (a - xMax % a) % a);
    return ans;
  }

}
