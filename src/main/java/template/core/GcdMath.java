package template.core;

/**
 * @author hum
 */
public class GcdMath {
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
}
