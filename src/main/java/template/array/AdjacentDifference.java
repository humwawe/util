package template.array;

/**
 * @author hum
 */
public class AdjacentDifference {
  // a 下标从0开始
  int[] oneDiff(int[] a) {
    int n = a.length;
    // 从0开始，最后一位空出来供区间操作
    // 有效数位为[0，n-1], 通过diff求前缀和时下标保持一致，从0开始到n-1，此时sum[n]=a[n-1]
    int[] diff = new int[n + 1];
    for (int i = 0; i < n; i++) {
      int x = 0;
      if (i > 0) {
        x = a[i - 1];
      }
      diff[i] = a[i] - x;
    }
    return diff;
  }

  // l, r 从1开始算，对 l 到 r 加 v
  void oneChange(int[] diff, int l, int r, int v) {
    diff[l - 1] += v;
    diff[r] -= v;
  }

  int[][] twoDiff(int[][] a) {
    int n = a.length;
    int m = a[0].length;
    int[][] diff = new int[n + 1][m + 1];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        int x = 0;
        int y = 0;
        int z = 0;
        if (i > 0) {
          x = a[i - 1][j];
        }
        if (j > 0) {
          y = a[i][j - 1];
        }
        if (i > 0 && j > 0) {
          z = a[i - 1][j - 1];
        }
        diff[i][j] = a[i][j] - x - y + z;
      }
    }
    return diff;
  }

  //从左上(x1,y1) 到右下(x2,y2) 对差分数组进行操作，从1开始
  void twoChange(int[][] diff, int x1, int y1, int x2, int y2, int v) {
    diff[x1 - 1][y1 - 1] += v;
    diff[x1 - 1][y2] -= v;
    diff[x2][y1 - 1] -= v;
    diff[x2][y2] += v;
  }


}
