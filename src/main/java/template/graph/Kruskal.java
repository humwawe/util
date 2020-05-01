package template.graph;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 时间复杂度是  O(mlogm)
 *
 * @author hum
 */
public class Kruskal {
    int N = 105;
    int[] p = new int[N];
    // n,m,edges 都需要初始化
    int n, m;
    Edge[] edges;

    int kruskal() {
        Arrays.sort(edges, Comparator.comparingInt(a -> a.w));
        for (int i = 1; i <= n; i++) {
            p[i] = i;
        }
        int res = 0, cnt = 0;
        for (int i = 0; i < m; i++) {
            int a = edges[i].a;
            int b = edges[i].b;
            int w = edges[i].w;
            a = find(a);
            b = find(b);
            // 如果两个连通块不连通，则将这两个连通块合并
            if (a != b) {
                res += w;
                p[a] = b;
                cnt++;
            }
        }
        if (cnt < n - 1) {
            return 0x3f3f3f3f;
        }
        return res;
    }

    int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }
}

class Edge {
    int a;
    int b;
    int w;

    public Edge(int a, int b, int w) {
        this.a = a;
        this.b = b;
        this.w = w;
    }
}
