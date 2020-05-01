package template.graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author hum
 */
public class Dijkstra {
    int N = 105;

    int[] dist = new int[N];
    boolean[] vis = new boolean[N];

    // 存储每条边，不存在需要初始化值为0x3f3f3f3f
    int[][] g = new int[N][N];

    int M = 2 * N;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;

    int n;

    // 求1号点到n个点的全部最短路，并返回n号点的距离，如果不存在则返回-1
    // 复杂度与点相关O(n^2)，适合稠密图，用矩阵存储
    int dijkstra1() {
        Arrays.fill(dist, 0x3f3f3f3f);
        dist[1] = 0;
        // 循环依次可以求一个点，因为1号点已经加入，因此只需要循环n-1次，如果多循环一次也不影响结果
        for (int i = 0; i < n - 1; i++) {
            // 在还未确定最短路的点中，寻找距离最小的点
            int t = -1;
            for (int j = 1; j <= n; j++) {
                if (!vis[j] && (t == -1 || dist[j] < dist[t])) {
                    t = j;
                }
            }
            if (t == -1) {
                break;
            }
            vis[t] = true;
            // 用t更新其他点的距离，如果要记录前一个节点，在更新dist的时候处理
            for (int j = 1; j <= n; j++) {
                if (!vis[j]) {
                    dist[j] = Math.min(dist[j], dist[t] + g[t][j]);
                }
            }
        }
        if (dist[n] == 0x3f3f3f3f) {
            return -1;
        }
        return dist[n];
    }

    // 堆优化版，复杂度O(mlogn)，适合邻接矩阵
    // 注意初始化图的时候，先将head数组置为-1，idx置位0
    int dijkstra2() {
        Arrays.fill(dist, 0x3f3f3f3f);
        dist[1] = 0;
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        priorityQueue.add(new int[]{0, 1});
        while (!priorityQueue.isEmpty()) {
            int[] poll = priorityQueue.poll();
            int d = poll[0];
            int v = poll[1];
            if (vis[v]) {
                continue;
            }
            vis[v] = true;
            for (int i = h[v]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dist[j] > d + w[i]) {
                    dist[j] = d + w[i];
                    priorityQueue.add(new int[]{dist[j], j});
                }
            }
        }
        if (dist[n] == 0x3f3f3f3f) {
            return -1;
        }
        return dist[n];
    }

    void add(int a, int b, int v) {
        e[idx] = b;
        w[idx] = v;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
