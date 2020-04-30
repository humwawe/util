package template.graph;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
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

    // 求1号点到n号点的最短路距离，如果从1号点无法走到n号点则返回-1
    int spfa(int n) {
        Arrays.fill(dist, 0x3f3f3f3f);
        dist[1] = 0;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(1);
        vis[1] = true;
        while (!priorityQueue.isEmpty()) {
            int t = priorityQueue.poll();
            vis[t] = false;
            for (int i = h[t]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dist[j] > dist[t] + w[i]) {
                    dist[j] = dist[t] + w[i];
                    if (!vis[j]) {
                        priorityQueue.add(j);
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

    // 当前最短路边的数量
    int[] cnt = new int[N];

    // 是否存在负环回路(总共n个点)
    boolean spfaHasCircle(int n) {
        // 保证负环能可达（只放1，从1开始可能到不了负环）
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            vis[i] = true;
            priorityQueue.add(i);
        }
        while (!priorityQueue.isEmpty()) {
            int t = priorityQueue.poll();
            vis[t] = false;
            for (int i = h[t]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dist[j] > dist[t] + w[i]) {
                    dist[j] = dist[t] + w[i];
                    cnt[j] = cnt[t] + 1;
                    if (cnt[j] >= n) {
                        return true;
                    }
                    if (!vis[j]) {
                        priorityQueue.add(j);
                        vis[j] = true;
                    }
                }
            }
        }

        return false;
    }

}
