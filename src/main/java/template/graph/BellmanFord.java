package template.graph;

import java.util.Arrays;

/**
 * 时间复杂度 O(nm)
 *
 * @author hum
 */
public class BellmanFord {
    int N = 50;
    int M = 2 * N;
    int[] dist = new int[N];

    int[] u = new int[M];
    int[] v = new int[M];
    int[] wei = new int[M];
    int m;

    int BellmanFord(int n) {
        Arrays.fill(dist, 0x3f3f3f3f);
        dist[1] = 0;
        // 更新n-1次
        // 存在负权回路可以多一次判断，如果第n次迭代仍然会松弛三角不等式，就说明存在一条长度是n+1的最短路径，由抽屉原理，路径中至少存在两个相同的点，说明图中存在负权回路
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < m; j++) {
                int a = u[j], b = v[j], w = wei[j];
                if (dist[b] > dist[a] + w) {
                    dist[b] = dist[a] + w;
                }
            }
        }
        // 因为存在负边，所有可能有dist小于inf，一般除以2可以保证仍是大数
        if (dist[n] > 0x3f3f3f3f / 2) {
            return -1;
        }
        return dist[n];
    }


    int k;
    int[] oldDist = new int[N];

    // 最多k次的最短路径
    int BellmanFordK(int n) {
        Arrays.fill(dist, 0x3f3f3f3f);
        dist[1] = 0;
        for (int i = 0; i < k; i++) {
            // 需要有backup数组，保证每次用的上一次的更新结果
            // 如果没有k限制，则不需要，因为用当前的dist更新不会影响最后的最短距离（会影响当前步，考虑1->2:1 2->3:1 1->3:3的图例只走一步则1->3为3）
            oldDist = Arrays.copyOfRange(dist, 0, N);
            for (int j = 0; j < m; j++) {
                int a = u[j], b = v[j], w = wei[j];
                if (dist[b] > oldDist[a] + w) {
                    dist[b] = oldDist[a] + w;
                }
            }
        }
        if (dist[n] > 0x3f3f3f3f / 2) {
            return -1;
        }
        return dist[n];
    }
}
