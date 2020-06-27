package template.graph;


import java.util.Stack;

/**
 * @author hum
 */
public class Tarjan {
    int N = 105;
    int[] dfn = new int[N];
    int[] low = new int[N];
    ;
    int timestamp = 0;
    Stack<Integer> stack = new Stack<>();
    boolean[] inStack = new boolean[N];
    int[] h = new int[N];
    int[] e = new int[N];
    int[] ne = new int[N];
    // 记录强连通个数
    int sccCnt = 0;
    // 某个点属于哪个强连通分量
    int[] id = new int[N];

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
}
