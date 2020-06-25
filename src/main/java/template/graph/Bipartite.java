package template.graph;

import java.util.Arrays;

/**
 * @author hum
 */
public class Bipartite {
    int N = 105;
    int M = 2 * N;
    // 注意初始化图的时候，先将head数组置为-1，idx置位0
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    // 表示每个点的颜色，-1表示未染色，0表示白色，1表示黑色
    int[] color = new int[N];

    int n;

    // 是否是二分图（染色法）
    // 当且仅当图没有奇数个元素构成的环，可以组成二分图
    boolean check() {
        Arrays.fill(color, -1);
        boolean flag = true;
        for (int i = 1; i <= n; i++) {
            if (color[i] == -1) {
                if (!dfs(i, 0)) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    // 参数：u表示当前节点，c表示当前点的颜色
    boolean dfs(int u, int c) {
        color[u] = c;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (color[j] == -1) {
                if (!dfs(j, 1 - c)) {
                    return false;
                }
            } else if (color[j] == c) {
                return false;
            }
        }
        return true;
    }

    // n1表示第一个集合中的点数，n2表示第二个集合中的点数
    int n1, n2;
    // 存储第二个集合中的每个点当前匹配的第一个集合中的点是哪个
    int[] match = new int[N];
    // 表示第二个集合中的每个点是否已经被遍历过e
    boolean[] vis = new boolean[N];

    // 匈牙利算法中只会用到从第一个集合指向第二个集合的边，所以只用存一个方向的边
    int match() {
        int res = 0;
        for (int i = 1; i <= n1; i++) {
            vis = new boolean[N];
            if (find(i)) {
                res++;
            }
        }
        return res;
    }

    boolean find(int x) {
        for (int i = h[x]; i != -1; i = ne[i]) {
            int j = e[i];
            if (!vis[j]) {
                vis[j] = true;
                if (match[j] == 0 || find(match[j])) {
                    match[j] = x;
                    return true;
                }
            }
        }
        return false;
    }
}
