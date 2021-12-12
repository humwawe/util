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
    // 最大匹配边数 = 最小点覆盖点数
    // 最大独立集 = n - 最小点覆盖点数
    // 最小路径点覆盖 = n - 最大匹配边数

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
            // 多重匹配可以拆点或者网络流，特别的多对一的情况（假设左为多）可以对左边跑kl次find，若未找到提前break剪枝
            // for (int j = 0; j < kl; j++)
            Arrays.fill(vis, false);
            if (find(i)) {
                res++;
            }
            // else break;
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
                // 右部多重的话可以开二维数组，考虑记录匹配次数，超过后依次尝试增广路
                //  if (cnt[j] < v[j]) {
                //      match[j][++cnt[j]] = x;
                //      return true;
                //  }
                //  for (int k = 1; k <= v[j]; k++) {
                //      if (find(match[j][k])) {
                //          match[j][k] = x;
                //          return true;
                //      }
                //  }
            }
        }
        return false;
    }

    // 边权
    int[][] w = new int[N][N];
    // 左右部点的顶标（顶点标记值）
    int[] la = new int[N];
    int[] lb = new int[N];
    int[] upd = new int[N];
    // 访问标记：是否在交错树中
    boolean[] va = new boolean[N];
    boolean[] vb = new boolean[N];
    int inf = 0x3f3f3f3f;

    // km在稠密图上效果很好，求带权最大匹配需满足是完备匹配的情况
    // O(n^2*m)
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
                Arrays.fill(va, false);
                Arrays.fill(vb, false);
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
                // 修改顶标
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

    boolean dfs(int x) {
        // 在交错树中
        va[x] = true;
        for (int y = 1; y <= n; y++) {
            if (!vb[y]) {
                // 相等子图
                if (la[x] + lb[y] - w[x][y] == 0) {
                    // 在交错树中
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

    // 右部点在交错树中的上一个右部点
    int[] last = new int[N];

    // 优化的km O(N^3)
    // 每次dfs失败后，下一次从相等子图刚刚加入的边（也就是那条delta最小的边）出发，继续搜索，而不是还从交错树的根开始
    int km2() {
        for (int i = 1; i <= n; i++) {
            la[i] = -inf;
            lb[i] = 0;
            for (int j = 1; j <= n; j++) {
                la[i] = Math.max(la[i], w[i][j]);
            }
        }
        for (int i = 1; i <= n; i++) {
            Arrays.fill(va, false);
            Arrays.fill(vb, false);
            Arrays.fill(upd, inf);
            int start = 0;
            match[0] = i;
            while (match[start] != 0) {
                if (dfs2(match[start], start)) {
                    break;
                }
                int delta = inf;
                for (int j = 1; j <= n; j++) {
                    if (!vb[j] && delta > upd[j]) {
                        delta = upd[j];
                        start = j;
                    }
                }
                // 修改顶标
                for (int j = 1; j <= n; j++) {
                    if (va[j]) {
                        la[j] -= delta;
                    }
                    if (vb[j]) {
                        lb[j] += delta;
                    } else {
                        upd[j] -= delta;
                    }
                }
                vb[start] = true;
            }
            while (start != 0) {
                match[start] = match[last[start]];
                start = last[start];
            }
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res += w[match[i]][i];
        }
        return res;
    }

    boolean dfs2(int x, int fa) {
        va[x] = true;
        for (int y = 1; y <= n; y++) {
            if (!vb[y]) {
                // 相等子图
                if (la[x] + lb[y] - w[x][y] == 0) {
                    vb[y] = true;
                    last[y] = fa;
                    if (match[y] == 0 || dfs2(match[y], y)) {
                        match[y] = x;
                        return true;
                    }
                } else {
                    if (upd[y] > la[x] + lb[y] - w[x][y]) {
                        upd[y] = la[x] + lb[y] - w[x][y];
                        last[y] = fa;
                    }
                }
            }
        }
        return false;
    }
}
