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
