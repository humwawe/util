package template.core;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author hum
 */
public class Dijkstra {
  public long inf = (long) 1e17;

  public long[] dijkstra(Graph graph, int s) {
    long[] dist = new long[graph.N];
    boolean[] vis = new boolean[graph.N];
    Arrays.fill(dist, inf);
    dist[s] = 0;
    PriorityQueue<long[]> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
    priorityQueue.add(new long[]{0, s});
    while (!priorityQueue.isEmpty()) {
      long[] poll = priorityQueue.poll();
      long d = poll[0];
      int v = (int) poll[1];
      if (vis[v]) {
        continue;
      }
      vis[v] = true;
      for (int i = graph.h[v]; i != -1; i = graph.ne[i]) {
        int j = graph.e[i];
        if (dist[j] > d + graph.w[i]) {
          dist[j] = d + graph.w[i];
          priorityQueue.add(new long[]{dist[j], j});
        }
      }
    }
    return dist;
  }

}