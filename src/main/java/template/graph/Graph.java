package template.graph;

import java.util.*;

/**
 * @author hum
 */
public class Graph {
    int n = 100010;
    int m = n * 2;
    // 对于每个点k，开一个单链表，存储k所有可以走到的点。h[k]存储这个单链表的头结点
    int[] h = new int[n];
    int[] e = new int[m];
    int[] ne = new int[m];
    int idx;

    public Graph() {
        idx = 0;
        Arrays.fill(h, -1);
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }

    void init2() {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        int[][] edges = new int[100][2];
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
