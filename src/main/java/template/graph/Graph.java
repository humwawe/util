package template.graph;

import java.util.*;

/**
 * @author hum
 */
public class Graph {
  int N = 100010;
  int M = N * 2;
  // 对于每个点k，开一个单链表，存储k所有可以一步走到的点。h[k]存储这个单链表的头结点
  int[] h = new int[N];
  int[] e = new int[M];
  int[] ne = new int[M];
  int[] w = new int[M];
  int idx;

  boolean[] vis = new boolean[N];

  public Graph() {
    idx = 0;
    Arrays.fill(h, -1);
  }

  //  int[] h, e, ne, w;
  //  int idx;

  //  int[][] g = packG(n, edges);
  //  h = g[0]; e = g[1]; ne = g[2]; w = g[3]; idx = g[4][0];
  int[][] packG(int n, int[][] edges) {
    int[] h = new int[n + 1];
    for (int i = 0; i <= n; i++) {
      h[i] = -1;
    }
    int m = edges.length;
    int M = m * 2 + 5;
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int[] idx = new int[]{0};

    for (int[] edge : edges) {
      int a = edge[0];
      int b = edge[1];
      int c = edge[2];

      e[idx[0]] = b;
      w[idx[0]] = c;
      ne[idx[0]] = h[a];
      h[a] = idx[0]++;

      e[idx[0]] = a;
      w[idx[0]] = c;
      ne[idx[0]] = h[b];
      h[b] = idx[0]++;
    }

    return new int[][]{h, e, ne, w, idx};
  }


  void add(int a, int b) {
    e[idx] = b;
    ne[idx] = h[a];
    h[a] = idx++;
  }

  void add(int a, int b, int c) {
    e[idx] = b;
    w[idx] = c;
    ne[idx] = h[a];
    h[a] = idx++;
  }

  void dfs(int u) {
    vis[u] = true;
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (!vis[j]) {
        dfs(j);
      }
    }
  }

  void bfs(int u) {
    int[] dist = new int[N];
    Arrays.fill(dist, -1);
    Queue<Integer> queue = new ArrayDeque<>();
    queue.add(u);
    dist[u] = 0;
    while (!queue.isEmpty()) {
      int p = queue.poll();
      for (int i = h[p]; i != -1; i = ne[i]) {
        int j = e[i];
        if (dist[j] == -1) {
          dist[j] = dist[p] + 1;
          queue.add(j);
        }
      }
    }
  }

  void init2() {
    Map<Integer, Set<Integer>> graph = new HashMap<>();
    int[][] edges = new int[100][3];
    for (int[] edge : edges) {
      int u = edge[0], v = edge[1];
      graph.computeIfAbsent(v, x -> new HashSet<>()).add(u);
      graph.computeIfAbsent(u, x -> new HashSet<>()).add(v);
    }

    Map<Integer, Map<Integer, Integer>> graph2 = new HashMap<>();
    for (int[] edge : edges) {
      int u = edge[0], v = edge[1], w = edge[2];
      graph2.computeIfAbsent(u, x -> new HashMap<>()).put(v, w);
      graph2.computeIfAbsent(v, x -> new HashMap<>()).put(u, w);
    }
  }
}
