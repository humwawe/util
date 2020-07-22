package template.graph;

/**
 * @author hum
 */
public class Km {
    int N = 5;
    // 边权
    int[][] w = new int[N][N];
    // 左右部点的顶标
    int[] la = new int[N];
    int[] lb = new int[N];
    // 访问标记：是否在交错树中
    boolean[] va = new boolean[N];
    boolean[] vb = new boolean[N];
    int[] match = new int[N];
    int n, delta;
    int[] upd = new int[N];
    int inf = 0x3f3f3f3f;

    boolean dfs(int x) {
        // 在交错树中
        va[x] = true;
        for (int y = 1; y <= n; y++) {
            if (!vb[y]) {
                // 相等子图
                if (la[x] + lb[y] - w[x][y] == 0) {
                    vb[y] = true;
                    if (match[y] == 0 || dfs(match[y])) {
                        match[y] = x;
                        return true;
                    }
                } else {
                    upd[y] = Math.min(upd[y], la[x] + lb[y] - w[x][y]);
                }
            }
        }
        return false;
    }

    int km() {
        for (int i = 1; i <= n; i++) {
            la[i] = -inf;
            lb[i] = 0;
            for (int j = 1; j <= n; j++) {
                la[i] = Math.max(la[i], w[i][j]);
            }
        }
        for (int i = 1; i <= n; i++) {
            // 直到左部顶点找到匹配
            while (true) {
                va = new boolean[N];
                vb = new boolean[N];
                for (int j = 1; j <= n; j++) {
                    upd[j] = inf;
                }
                if (dfs(i)) {
                    break;
                }
                int delta = inf;
                for (int j = 1; j <= n; j++) {
                    if (!vb[j]) {
                        delta = Math.min(delta, upd[j]);
                    }
                }
                for (int j = 1; j <= n; j++) {
                    if (va[j]) {
                        la[j] -= delta;
                    }
                    if (vb[j]) {
                        lb[j] += delta;
                    }
                }
            }
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res += w[match[i]][i];
        }
        return res;
    }
}
