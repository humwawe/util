package template.math;

import java.util.*;

/**
 * @author hum
 */
public class CombinationMath {
  private static final int MEMO_THRESHOLD = 1000000;
  static long mod = (long) 1e9 + 7;
  private static final List<Long> inv = new ArrayList<>();
  private static final List<Long> fact = new ArrayList<>();
  private static final List<Long> invFact = new ArrayList<>();
  private static final Map<Long, List<Long>> pow = new HashMap<>();

  private static void buildInvTable(int n) {
    if (inv.isEmpty()) {
      inv.add(null);
      inv.add(1L);
    }
    for (int i = inv.size(); i <= n; i++) {
      inv.add(mod - inv.get((int) (mod % i)) * (mod / i) % mod);
    }
  }

  private static void buildFactTable(int n) {
    if (fact.isEmpty()) {
      fact.add(1L);
      invFact.add(1L);
    }
    for (int i = fact.size(); i <= n; i++) {
      fact.add(fact.get(i - 1) * i % mod);
      invFact.add(inv(fact.get(i)));
    }
  }

  public static void setupPowTable(long a) {
    pow.put(a, new ArrayList<>(Collections.singleton(1L)));
  }

  private static void rangeCheck(long n, long r) {
    if (n < r) {
      throw new IllegalArgumentException("n < r");
    }
    if (n < 0) {
      throw new IllegalArgumentException("n < 0");
    }
    if (r < 0) {
      throw new IllegalArgumentException("r < 0");
    }
  }

  public static long fact(int n) {
    buildFactTable(n);
    return fact.get(n);
  }

  public static long invFact(int n) {
    buildFactTable(n);
    return invFact.get(n);
  }

  private static long comb0(int n, int r) {
    rangeCheck(n, r);
    return fact(n) * invFact(r) % mod * invFact(n - r) % mod;
  }

  public static long comb(long n, long r) {
    rangeCheck(n, r);
    if (n < MEMO_THRESHOLD) {
      return comb0((int) n, (int) r);
    }
    r = Math.min(r, n - r);
    long x = 1, y = 1;
    for (long i = 1; i <= r; i++) {
      x = x * (n - r + i) % mod;
      y = y * i % mod;
    }
    return x * inv(y) % mod;
  }

  private static long perm0(int n, int r) {
    rangeCheck(n, r);
    return fact(n) * invFact(n - r) % mod;
  }

  public static long perm(long n, long r) {
    rangeCheck(n, r);
    if (n < MEMO_THRESHOLD) {
      return perm0((int) n, (int) r);
    }
    long x = 1;
    for (long i = 1; i <= r; i++) {
      x = x * (n - r + i) % mod;
    }
    return x;
  }

  public static long homo(long n, long r) {
    return r == 0 ? 1 : comb(n + r - 1, r);
  }

  private static long inv0(int a) {
    buildInvTable(a);
    return inv.get(a);
  }

  public static long inv(long a) {
    if (a < MEMO_THRESHOLD) {
      return inv0((int) a);
    }
    long b = mod;
    long u = 1, v = 0;
    while (b >= 1) {
      long t = a / b;
      a -= t * b;
      u -= t * v;
      if (a < 1) {
        return (v %= mod) < 0 ? v + mod : v;
      }
      t = b / a;
      b -= t * a;
      v -= t * u;
    }
    return (u %= mod) < 0 ? u + mod : u;
  }

  public static long pow(long a, long b) {
    if (pow.containsKey(a) && b < MEMO_THRESHOLD) {
      return powMemo(a, (int) b);
    }
    long x = 1;
    while (b > 0) {
      if (b % 2 == 1) {
        x = x * a % mod;
      }
      a = a * a % mod;
      b >>= 1;
    }
    return x;
  }

  private static long powMemo(long a, int b) {
    List<Long> powMemo = pow.get(a);
    while (powMemo.size() <= b) {
      powMemo.add(powMemo.get(powMemo.size() - 1) * a % mod);
    }
    return powMemo.get(b);
  }

  // a一般是某两个个mod以内的值计算后的结果，不会超过mod太多，while很快结束，否则需要 %mod 计算
  public static long mod(long a) {
    while (a >= mod) {
      a -= mod;
    }
    while (a < 0) {
      a += mod;
    }
    return a;
  }

}
