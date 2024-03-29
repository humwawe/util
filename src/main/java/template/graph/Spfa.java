package template.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * 队列优化的BellmanFord算法
 * 平均情况下 O(m)，最坏情况下 O(nm)
 *
 * @author hum
 */
public class Spfa {
  int N = 105;

  int[] dist = new int[N];
  boolean[] vis = new boolean[N];

  int M = 2 * N;
  // 注意初始化图的时候，先将head数组置为-1，idx置位0
  int[] h = new int[N];
  int[] e = new int[M];
  int[] ne = new int[M];
  int[] w = new int[M];
  int idx;


  void add(int a, int b, int c) {
    e[idx] = b;
    w[idx] = c;
    ne[idx] = h[a];
    h[a] = idx++;
  }

  int n;

  // 求1号点到n号点的最短路距离，如果从1号点无法走到n号点则返回-1
  int spfa() {
    Arrays.fill(dist, 0x3f3f3f3f);
    dist[1] = 0;
    Queue<Integer> queue = new ArrayDeque<>();
    queue.add(1);
    // 暴力的扩展，vis 维护点是否在队列中，在队列中即可不再放入
    vis[1] = true;
    while (!queue.isEmpty()) {
      int t = queue.poll();
      vis[t] = false;
      for (int i = h[t]; i != -1; i = ne[i]) {
        int j = e[i];
        if (dist[j] > dist[t] + w[i]) {
          dist[j] = dist[t] + w[i];
          if (!vis[j]) {
            queue.add(j);
            vis[j] = true;
          }
        }
      }
    }
    if (dist[n] == 0x3f3f3f3f) {
      return -1;
    }
    return dist[n];
  }

  // 当前到x点的最短路包含边的数量，n个点的最短路如果没有负环最多有n-1条边
  int[] cnt = new int[N];

  // 是否存在负环回路(总共n个点)
  boolean spfaHasCircle(int n) {
    // int tot = 0;
    // 保证负环能可达（只放1，从1开始可能到不了负环，图不联通）
    // 卡spfa 可以使用deque试，每次从最末尾弹出
    Queue<Integer> queue = new ArrayDeque<>();
    for (int i = 1; i <= n; i++) {
      vis[i] = true;
      queue.add(i);
    }
    while (!queue.isEmpty()) {
      int u = queue.poll();
      vis[u] = false;
      for (int i = h[u]; i != -1; i = ne[i]) {
        int j = e[i];
        if (dist[j] > dist[u] + w[i]) {
          dist[j] = dist[u] + w[i];
          cnt[j] = cnt[u] + 1;
          // 有时候spfa会超时，考虑限制队列大小，如果超过一定次数即有负环
          // tot++;
          // if (tot >= 1e5) {
          // return true;
          // }
          // 保险可以 cnt[j] > n
          if (cnt[j] >= n) {
            return true;
          }
          if (!vis[j]) {
            queue.add(j);
            vis[j] = true;
          }
        }
      }
    }
    return false;
  }

  // dfs判断负环，有时候比bfs的方法要快
  boolean dfsSpfaHasCircle(int n) {
    for (int i = 1; i <= n; i++) {
      if (dfs(i)) {
        return true;
      }
    }
    return false;
  }

  boolean dfs(int u) {
    vis[u] = true;
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (dist[j] > dist[u] + w[i]) {
        dist[j] = dist[u] + w[i];
        if (vis[j] || dfs(j)) {
          return true;
        }
      }
    }
    vis[u] = false;
    return false;
  }
}
