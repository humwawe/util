package template.graph;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author hum
 */
public class Lca {
  int N = (int) (1e5 + 5);
  int M = 2 * N;
  int[] h = new int[N];
  int[] e = new int[M];
  int[] ne = new int[M];
  int idx;
  // (int)log(N)+1
  int T = 18;
  int[] d = new int[N];
  int[][] f = new int[N][T];

  void bfs() {
    Queue<Integer> queue = new ArrayDeque<>();
    queue.add(1);
    d[1] = 1;
    while (!queue.isEmpty()) {
      Integer u = queue.poll();
      for (int i = h[u]; i != -1; i = ne[i]) {
        int j = e[i];
        if (d[j] == 0) {
          d[j] = d[u] + 1;
          f[j][0] = u;
          for (int k = 1; k < T; k++) {
            f[j][k] = f[f[j][k - 1]][k - 1];
          }
          queue.add(j);
        }
      }
    }
  }

  private int lca(int a, int b) {
    if (d[a] < d[b]) {
      int t = a;
      a = b;
      b = t;
    }
    // 走到统一层
    for (int i = T - 1; i >= 0; i--) {
      if (d[f[a][i]] >= d[b]) {
        a = f[a][i];
      }
    }
    if (a == b) {
      return a;
    }
    for (int i = T - 1; i >= 0; i--) {
      if (f[a][i] != f[b][i]) {
        a = f[a][i];
        b = f[b][i];
      }
    }
    return f[a][0];
  }

  int M2 = (int) 1e4 + 5;
  int[] vis = new int[N];
  // 注意并查集初始化 fa[i]=i
  int[] fa = new int[N];
  int[] h2 = new int[N];
  int[] e2 = new int[M2];
  int[] ne2 = new int[M2];
  int idx2;
  int[] queryId = new int[M2];
  // m个查询
  int m;
  int[] lca = new int[m];

  // 基于dfs
  // 如果在遍历的时候发现是所求的点，考虑之前遍历过的点路径压缩后的顶点
  void tarjan(int u) {
    vis[u] = 1;
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (vis[j] != 0) {
        continue;
      }
      d[j] = d[u] + 1;
      tarjan(j);
      fa[j] = u;
    }
    // 考虑每对需要求的点，是否已经遍历过
    for (int i = h2[u]; i != -1; i = ne2[i]) {
      int j = e2[i];
      if (vis[j] == 2) {
        lca[queryId[i]] = find(j);
      }
    }
    vis[u] = 2;
  }

  int find(int x) {
    if (fa[x] != x) {
      fa[x] = find(fa[x]);
    }
    return fa[x];
  }

  // 离线处理，加正反
  void add(int a, int b, int id) {
    if (a == b) {
      lca[id] = a;
      return;
    }
    e2[idx2] = b;
    ne2[idx2] = h2[a];
    queryId[idx2] = id;
    h2[a] = idx2++;

    e2[idx2] = a;
    ne2[idx2] = h2[b];
    queryId[idx2] = id;
    h2[b] = idx2++;
  }

}
