package template.math;

/**
 * @author hum
 */
public class Gcd {
  int gcd(int a, int b) {
    return b != 0 ? gcd(b, a % b) : a;
  }

  int x, y;

  // 求x, y，使得ax + by = gcd(a, b)
  // ax + by = m 成立，则m是gcd(a, b)的倍数
  // 返回d是最大公约数
  // x,y为一组解，更多的解可以xOther=x-b/d*k,yOther=y+b/d*k (k为整数)
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
}

