package template.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * @author hum
 */
public class Dinic {
    int N = 105;
    int M = 2 * N;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] f = new int[M];
    int[] depth = new int[N];
    int idx;
    int inf = 0x3f3f3f3f;

    int dicnic(int u, int t) {
        int ret = 0;
        while (bfs(u, t)) {
            ret += dfs(u, inf, t);
        }
        return ret;
    }

    boolean bfs(int s, int t) {
        Arrays.fill(depth, 0);
        Queue<Integer> q = new ArrayDeque<>();
        depth[s] = 1;
        q.add(s);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (depth[j] == 0 && f[i] > 0) {
                    depth[j] = depth[u] + 1;
                    q.add(j);
                }
            }
        }
        return depth[t] != 0;
    }

    int dfs(int u, int flow, int t) {
        if (u == t) {
            return flow;
        }
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (depth[j] == depth[u] + 1 && f[i] > 0) {
                int d = dfs(j, Math.min(flow, f[i]), t);
                if (d > 0) {
                    f[i] -= d;
                    f[i ^ 1] += d;
                    return d;
                }
            }
        }
        return 0;
    }

    void add(int a, int b, int w) {
        e[idx] = b;
        ne[idx] = h[a];
        f[idx] = w;
        h[a] = idx++;
        e[idx] = a;
        ne[idx] = h[b];
        f[idx] = 0;
        h[b] = idx++;
    }

}
