package template.graph;


/**
 * @author hum
 */
public class Euler {
    int N = 105;
    int M = 205;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] w = new int[M];
    int[] ne = new int[M];
    boolean[] vis = new boolean[M];
    // 保存欧拉回路结果
    int[] res = new int[M];
    int t = 0;

    // 普通写法，一个点可能被遍历多次，而每次都扫描了它相邻的边
    void dfs(int u) {
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (!vis[i]) {
                vis[i] = vis[i ^ 1] = true;
                dfs(j);
            }
        }
        // 入栈
        res[t++] = u;
    }

    // 优化，每次访问后，及时修改head指向下一个
    void dfs2(int u) {
        while (true) {
            int i = h[u];
            while (i != -1 && vis[i]) {
                i = ne[i];
            }
            if (i != -1) {
                h[u] = ne[i];
                vis[i] = vis[i ^ 1] = true;
                int j = e[i];
                dfs2(j);
            } else {
                break;
            }
        }
        res[t++] = u;
    }

    // 用w[i]记录边序号
    // 递归的时候传入可以使res直接记录边的序号
    // dfs3(start, -1)，结果逆序输出即可，最后一个点为-1，输出时候跳过
    void dfs3(int u, int pre) {
        while (true) {
            int i = h[u];
            while (i != -1 && vis[i]) {
                i = ne[i];
            }
            if (i != -1) {
                h[u] = ne[i];
                vis[i] = vis[i ^ 1] = true;
                int j = e[i];
                dfs3(j, w[i]);
            } else {
                break;
            }
        }
        res[t++] = pre;
    }

    // 非递归防止爆栈
    int[] stack = new int[M];
    int top;

    void euler() {
        stack[++top] = 1;
        while (top > 0) {
            int u = stack[top];
            int i = h[u];
            while (i != -1 && vis[i]) {
                i = ne[i];
            }
            if (i != -1) {
                h[u] = ne[i];
                vis[i] = vis[i ^ 1] = true;
                stack[++top] = e[i];
            } else {
                // u所有边已访问，模拟回溯，并记录答案
                res[t++] = u;
                top--;
            }
        }
    }


}
