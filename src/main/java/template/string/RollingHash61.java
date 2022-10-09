package template.string;

/**
 * @author hum
 */
public class RollingHash61 {
  final long mod = (1L << 61) - 1;
  public long M;
  public long[] pows;
  public long[] hs;
  public int hp;

  // RollingHash61 rh = new RollingHash61(131, n);
  public RollingHash61(long M, int n) {
    assert M > 0;
    assert n > 0;
    this.M = M;
    this.pows = makePows(M, n);
    this.hs = new long[n + 1];
    this.hp = 0;
  }

  public long mul(long a, long b) {
    long al = a & (1L << 31) - 1, ah = a >>> 31;
    long bl = b & (1L << 31) - 1, bh = b >>> 31;
    long low = al * bl; // <2^62
    long mid = al * bh + bl * ah; // < 2^62
    long high = ah * bh + (mid >>> 31); // < 2^60 + 2^31 < 2^61
    // high*2^62 = high*2 (mod 2^61-1)
    return mod(mod(2 * high + low) + ((mid & (1L << 31) - 1) << 31));
  }

  public long mod(long a) {
    while (a >= mod) {
      a -= mod;
    }
    while (a < 0) {
      a += mod;
    }
    return a;
  }

  private long[] makePows(long M, int n) {
    long[] ret = new long[n + 1];
    ret[0] = 1;
    for (int i = 1; i <= n; i++) {
      ret[i] = mul(ret[i - 1], M);
    }
    return ret;
  }

  public void add(long x) {
    hs[hp + 1] = mul(hs[hp], M) + x;
    if (hs[hp + 1] >= mod) {
      hs[hp + 1] -= mod;
    }
    hp++;
  }

  public void add(String x) {
    for (int i = 0; i < x.length(); i++) {
      add(x.charAt(i));
    }
  }

  //  计算子串 str[l, r) 从0开始
  public long get(int l, int r) {
    assert l <= r;
    return mod(hs[r] - mul(hs[l], pows[r - l]));
  }

}
