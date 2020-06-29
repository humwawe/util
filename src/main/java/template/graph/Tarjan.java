package template.graph;


import java.util.Stack;

/**
 * @author hum
 */
public class Tarjan {
    int N = 105;
    int M = 205;
    int[] dfn = new int[N];
    int[] low = new int[N];

    int timestamp = 0;
    Stack<Integer> stack = new Stack<>();
    boolean[] inStack = new boolean[N];
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    // 记录强连通个数
    int sccCnt = 0;
    // 某个点属于哪个强连通分量
    int[] id = new int[N];

    // 有向图的强联通分量
    void tarjan(int u) {
        dfn[u] = low[u] = ++timestamp;
        stack.push(u);
        inStack[u] = true;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (dfn[j] == 0) {
                tarjan(j);
                low[u] = Math.min(low[u], low[j]);
            } else if (inStack[j]) {
                low[u] = Math.min(low[u], dfn[j]);
            }
        }
        // 联通分量编号递减即是拓扑序
        if (dfn[u] == low[u]) {
            int y;
            ++sccCnt;
            do {
                y = stack.pop();
                inStack[y] = false;
                id[y] = sccCnt;
            } while (y != u);
        }
    }

    int bridgeCnt = 0;
    boolean[] cut = new boolean[N];
    //删除一个点后增加的连通块
    int[] addBlock = new int[N];
    boolean[] bridge = new boolean[M];

    // 无向图的桥和割点
    void tarjan(int u, int prev) {
        dfn[u] = low[u] = ++timestamp;
        int son = 0;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (j == prev) {
                continue;
            }
            if (dfn[j] == 0) {
                son++;
                tarjan(j, u);
                low[u] = Math.min(low[u], low[j]);
                // 桥
                if (low[j] > dfn[u]) {
                    bridgeCnt++;
                    bridge[i] = true;
                    bridge[i ^ 1] = true;
                }
                // 割点
                // 非根节点
                if (u != prev && low[j] >= dfn[u]) {
                    cut[u] = true;
                    addBlock[u]++;
                }

            } else {
                low[u] = Math.min(low[u], dfn[j]);
            }
        }
        // 根节点，根据遍历树的儿子节点判断
        if (u == prev && son > 1) {
            cut[u] = true;
        }
        if (u == prev) {
            addBlock[u] = son - 1;
        }
    }

}
