package template.array;

/**
 * @author hum
 */
public class PartialSum {
  // a 下标从0开始
  int[] oneSum(int[] a) {
    int n = a.length;
    int[] sum = new int[n + 1];
    for (int i = 0; i < n; i++) {
      sum[i + 1] = sum[i] + a[i];
    }
    return sum;
  }

  // l, r 从1开始算
  int oneQuery(int[] sum, int l, int r) {
    return sum[r] - sum[l - 1];
  }

  int[][] twoSum(int[][] a) {
    int n = a.length;
    int m = a[0].length;
    int[][] sum = new int[n + 1][m + 1];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        sum[i + 1][j + 1] = sum[i][j + 1] + sum[i + 1][j] - sum[i][j] + a[i][j];
      }
    }
    return sum;
  }

  // 从左上(x1,y1) 到右下(x2,y2)的矩阵和
  int twoQuery(int[][] sum, int x1, int y1, int x2, int y2) {
    return sum[x2][y2] + sum[x1 - 1][y1 - 1] - sum[x1 - 1][y2] - sum[x2][y1 - 1];
  }

}
