package template.core;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * @author hum
 */
public class Spfa {
  public long inf = (long) 1e17;
  // 最短路或最长路
  boolean f = false;
  // 先进先出或者后进先出
  boolean type = false;
  Deque<Integer> queue = new ArrayDeque<>();

  public Spfa() {
  }

  public Spfa(boolean f) {
    this.f = f;
  }

  public Spfa(boolean f, boolean type) {
    this.f = f;
    this.type = type;
  }


  public long[] spfa(Graph graph, int s) {
    long[] dist = new long[graph.N];
    boolean[] vis = new boolean[graph.N];
    int[] cnt = new int[graph.N];
    if (!f) {
      Arrays.fill(dist, inf);
    }
    dist[s] = 0;
    queue.add(s);
    vis[s] = true;
    while (!queue.isEmpty()) {
      int u = poll();
      vis[u] = false;
      for (int i = graph.h[u]; i != -1; i = graph.ne[i]) {
        int j = graph.e[i];
        if (comp(dist[j], dist[u] + graph.w[i])) {
          dist[j] = dist[u] + graph.w[i];
          cnt[j] = cnt[u] + 1;
          // n
          if (cnt[j] >= graph.N) {
            return new long[]{-1};
          }
          if (!vis[j]) {
            queue.add(j);
            vis[j] = true;
          }
        }
      }
    }

    return dist;
  }

  private boolean comp(long l, long r) {
    return f ? l < r : l > r;
  }

  private Integer poll() {
    return type ? queue.pollLast() : queue.pollFirst();
  }

}
