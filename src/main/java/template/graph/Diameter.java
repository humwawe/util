package template.graph;

import java.util.*;

/**
 * @author hum
 */
public class Diameter {
  int N = 105;
  int M = N * 2;

  int[] h = new int[N];
  int[] e = new int[M];
  int[] ne = new int[M];
  int[] w = new int[M];
  int idx;
  int n;

  int[] dis = new int[N];
  int[] pre = new int[M];

  // 返回bfs最远的点，可以两次bfs求直径，不能有负数

  private int bfs(int u) {
    Arrays.fill(dis, -1);
    Queue<Integer> queue = new ArrayDeque<>();
    queue.add(u);
    dis[u] = 0;
    while (!queue.isEmpty()) {
      int cur = queue.poll();
      for (int i = h[cur]; i != -1; i = ne[i]) {
        int j = e[i];
        if (dis[j] == -1) {
          dis[j] = dis[cur] + w[i];
          // 记录转移下标
          pre[j] = i;
          queue.add(j);
        }
      }
    }
    int p = 0;
    for (int i = 1; i <= n; i++) {
      if (dis[p] < dis[i]) {
        p = i;
      }
    }
    return p;
  }

  List<Integer> getDiameter() {
    int p = bfs(1);
    int q = bfs(p);
    List<Integer> path = new ArrayList<>();
    while (q != p) {
      path.add(q);
      // w[pre[q]] = -1;
      // w[pre[q] ^ 1] = -1;
      q = e[pre[q] ^ 1];
    }
    path.add(p);
    return path;
  }

  int len = 0;

  // dp求直径，可以处理负数
  // dis[u] 存从u往下的最长路径
  // Arrays.fill(dis, 0);
  void dp(int u, int p) {
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (j == p) {
        continue;
      }
      dp(j, u);
      len = Math.max(len, dis[u] + dis[j] + w[i]);
      dis[u] = Math.max(dis[u], dis[j] + w[i]);
    }

  }
}
