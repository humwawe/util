package template.graph;


import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * @author hum
 */
public class NetworkFlow {
    int N = 105;
    int M = 2 * N;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] w = new int[M];
    int[] ne = new int[M];
    int idx;
    int maxflow;
    int s, t;
    int inf = 0x3f3f3f3f;
    // 正反加边: add(a, b, c); add(b, a, 0);

    // Edmonds-Karp增广路算法 O(nm^2)，一般较快，1e3到1e4的规模
    boolean[] vis = new boolean[N];
    // 增广路上各边的最小剩余容量
    int[] incf = new int[N];
    int[] pre = new int[N];

    int edmondsKarp() {
        while (bfsEK()) {
            updateEK();
        }
        return maxflow;
    }

    private boolean bfsEK() {
        Arrays.fill(vis, false);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(s);
        vis[s] = true;
        incf[s] = inf;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int i = h[u]; i != -1; i = ne[i]) {
                if (w[i] > 0) {
                    int j = e[i];
                    if (vis[j]) {
                        continue;
                    }
                    incf[j] = Math.min(incf[u], w[i]);
                    // 记录前驱，可以找到最长路的实际方案
                    pre[j] = i;
                    queue.add(j);
                    vis[j] = true;
                    if (j == t) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // 更新增广路及其反向边的剩余容量
    private void updateEK() {
        int x = t;
        while (x != s) {
            int i = pre[x];
            w[i] -= incf[t];
            w[i ^ 1] += incf[t];
            x = e[i ^ 1];
        }
        maxflow += incf[t];
    }

    // Dinic算法 O(n^2m)，一般较快，1e4到1e5的规模
    int[] d = new int[N];
    // 当前弧优化
    int[] now = new int[M];

    int dinic() {
        int flow;
        while (bfsDinic()) {
            while ((flow = dfsDinic(s, inf)) != 0) {
                maxflow += flow;
            }
        }
        return maxflow;
    }

    // 残差网络上构造分层图
    private boolean bfsDinic() {
        Arrays.fill(d, 0);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(s);
        d[s] = 1;
        now[s] = h[s];
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (w[i] > 0 && d[j] == 0) {
                    d[j] = d[u] + 1;
                    now[j] = h[j];
                    queue.add(j);
                    if (j == t) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int dfsDinic(int u, int flow) {
        if (u == t) {
            return flow;
        }
        int rest = flow;
        for (int i = now[u]; i != -1 && rest > 0; i = ne[i]) {
            // 当前弧优化（避免重复遍历从u出发不可扩展的边）
            now[u] = i;
            int j = e[i];
            if (w[i] > 0 && d[j] == d[u] + 1) {
                int k = dfsDinic(j, Math.min(rest, w[i]));
                // 剪枝，去掉增广完毕的点
                if (k == 0) {
                    d[j] = 0;
                }
                w[i] -= k;
                w[i ^ 1] += k;
                rest -= k;
            }
        }
        return flow - rest;
    }
}
