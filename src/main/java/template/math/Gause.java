package template.math;

/**
 * @author hum
 */
public class Gause {

  int n = 3;
  double[][] a = new double[n][n + 1];
  double eps = 1e-6;

  // 解存在a[i][n]中
  int gause() {
    int c, r;
    for (c = 0, r = 0; c < n; c++) {
      int t = r;
      // 找到绝对值最大的行
      for (int i = r; i < n; i++) {
        if (Math.abs(a[i][c]) > Math.abs(a[t][c])) {
          t = i;
        }
      }
      if (Math.abs(a[t][c]) < eps) {
        continue;
      }
      // 将绝对值最大的行换到最顶端
      for (int i = c; i <= n; i++) {
        double tmp = a[t][i];
        a[t][i] = a[r][i];
        a[r][i] = tmp;
      }
      // 将当前上的首位变成1
      for (int i = n; i >= c; i--) {
        a[r][i] /= a[r][c];
      }
      // 用当前行将下面所有的列消成0
      for (int i = r + 1; i < n; i++) {
        if (Math.abs(a[i][c]) > eps) {
          for (int j = n; j >= c; j--) {
            a[i][j] -= a[r][j] * a[i][c];
          }
        }
      }
      r++;
    }
    for (int i = r; i < n; i++) {
      if (Math.abs(a[i][n]) > eps) {
        // 无解
        return 2;
      }
    }
    if (r < n) {
      // 有无穷多组解
      return 1;
    }

    for (int i = n - 1; i >= 0; i--) {
      for (int j = i + 1; j < n; j++) {
        a[i][n] -= a[i][j] * a[j][n];
      }
    }
    // 有唯一解
    return 0;
  }

  // 异或空间的高斯消元，返回解的个数
  // 下标从0开始
  // a[i]第0位存等式右边的值
  int gauseXor(int[] a) {
    int row = a.length;
    int res = 1;
    for (int i = 0; i < row; i++) {
      // 找最大的主元
      for (int j = i + 1; j < row; j++) {
        if (a[j] > a[i]) {
          int tmp = a[i];
          a[i] = a[j];
          a[j] = tmp;
        }
      }
      // 消元完毕，有i个主元，row-i个自由元
      if (a[i] == 0) {
        res = 1 << (row - i);
        break;
      }
      // 出现0=1的方程
      if (a[i] == 1) {
        res = 0;
        break;
      }
      // a[i][0] 为方程右边的常量，因此k取到>0，若a[i]没有常量，则可以取到=0
      for (int k = 30; k > 0; k--) {
        if ((a[i] >> k & 1) == 1) {
          for (int j = 0; j < row; j++) {
            if (i != j && (a[j] >> k & 1) == 1) {
              a[j] ^= a[i];
            }
          }
          break;
        }
      }
    }
    return res;
  }

  // d存储数组a的异或线性基
  int[] d = new int[35];

  // 遍历数组a，对每个元素x使用add方法素插入
  // 插入成功返回true，否则为false
  boolean add(int x) {
    for (int i = 30; i >= 0; i--) {
      if (((x >> i) & 1) == 1) {
        if (d[i] != 0) {
          x ^= d[i];
        } else {
          d[i] = x;
          return true;
        }
      }
    }
    return false;
  }

}
