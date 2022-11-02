package template.graph;

/**
 * @author hum
 */
public class Floyd {
  int inf = (int) 1e8;

  // dist[k][i][j]：只允许经过[1,k]的点，i和j的距离，i,j不必在[1,k]中
  // 算法结束后，d[a][b]表示a到b的最短距离
  // 如果存在负权边，则无解一般可以用d[a][b]判断是否大于inf/2
  // 存在负环，可以看d[a][a]<0是否存在
  void floyd(int n) {
    int[][] dist = new int[n + 1][n + 1];
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        if (i == j) {
          dist[i][j] = 0;
        } else {
          dist[i][j] = inf;
        }
      }
    }

    // 对每个输入再赋值dist[][]

    for (int k = 0; k < n; k++) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
        }
      }
    }
  }

  int floydMinCircle(int n) {
    // 最短路矩阵
    int[][] dist = new int[n][n];
    int[][] val = new int[n][n];

    // 初始化最短路矩阵
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i == j) {
          dist[i][j] = val[i][j] = 0;
        } else {
          dist[i][j] = val[i][j] = inf;
        }
      }
    }
    // 对每个输入再赋值 dist[][] = val[][]

    int res = inf;
    for (int k = 0; k < n; k++) {
      for (int i = 0; i < k; i++) {
        for (int j = 0; j < i; j++) {
          // 更新答案，三个数相加避免超出范围（3*inf）
          res = Math.min(res, dist[i][j] + val[i][k] + val[k][j]);
        }
      }
      // 正常的 floyd 更新最短路矩阵
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
        }
      }
    }
    return res;
  }
}
