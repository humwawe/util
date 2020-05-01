package template.graph;

import java.util.Arrays;

/**
 * 时间复杂度是 O(n^2+m)
 *
 * @author hum
 */
public class Prim {
    int N = 105;

    int[] dist = new int[N];
    boolean[] vis = new boolean[N];

    // 存储每条边，不存在需要初始化值为0x3f3f3f3f
    int[][] g = new int[N][N];

    int n;

    // 如果图不连通，则返回0x3f3f3f3f, 否则返回最小生成树的树边权重之和
    int prim() {
        Arrays.fill(dist, 0x3f3f3f3f);
        int res = 0;
        // 每次确定一个点，初始位置未定，因此循环n次
        for (int i = 0; i < n; i++) {
            int t = -1;
            for (int j = 1; j <= n; j++) {
                if (!vis[j] && (t == -1 || dist[t] > dist[j])) {
                    t = j;
                }
            }
            if (i > 0 && dist[t] == 0x3f3f3f3f) {
                return 0x3f3f3f3f;
            }
            if (i > 0) {
                res += dist[t];
            }
            vis[t] = true;
            for (int j = 1; j <= n; j++) {
                if (!vis[j]) {
                    dist[j] = Math.min(dist[j], g[t][j]);
                }
            }
        }
        return res;
    }
}
