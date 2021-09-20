package template.graph;


import java.util.Arrays;
import java.util.Stack;

/**
 * @author hum
 */
public class Tarjan {
    int N = 105;
    int M = 205;
    int[] dfn = new int[N];
    int[] low = new int[N];

    int timestamp;
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


    boolean[] bridge = new boolean[M];

    // 无向图的桥 tarjanBridge(root, -1)
    void tarjanBridge(int u, int inEdge) {
        dfn[u] = low[u] = ++timestamp;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            // 搜索树边
            if (dfn[j] == 0) {
                tarjanBridge(j, i);
                low[u] = Math.min(low[u], low[j]);
                // 桥
                if (low[j] > dfn[u]) {
                    bridge[i] = true;
                    bridge[i ^ 1] = true;
                }
            } else if (i != (inEdge ^ 1)) {
                low[u] = Math.min(low[u], dfn[j]);
            }
        }
    }

    // 无向图边双联通分量（e-DCC）
    // 根据桥来划分
    // 某个x所属双联通编号
    int[] eDccC = new int[N];
    int eDccNo;

    // 对每个边双联通分量编号
    void tarjanBridgeEDccNo(int n) {
        // 对所有的点(1-n)
        for (int i = 1; i <= n; i++) {
            if (eDccC[i] == 0) {
                // 编号
                ++eDccNo;
                tarjanBridgeEDccDfs(i);
            }
        }
    }

    void tarjanBridgeEDccDfs(int u) {
        eDccC[u] = eDccNo;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (eDccC[j] != 0 || bridge[i]) {
                continue;
            }
            tarjanBridgeEDccDfs(j);
        }
    }

    // e-DCC缩点
    int[] eDccH = new int[N];
    int[] eDccE = new int[M];
    int[] eDccNe = new int[M];
    int eDccIdx;

    void tarjanBridgeEDccReBuild() {
        Arrays.fill(eDccH, -1);
        eDccIdx = 0;
        // 对每条边
        for (int i = 0; i < idx; i++) {
            int x = e[i];
            int y = e[i ^ 1];
            if (eDccC[x] == eDccC[y]) {
                continue;
            }
            addEDcc(eDccC[x], eDccC[y]);
        }
    }

    void addEDcc(int a, int b) {
        eDccE[eDccIdx] = b;
        eDccNe[eDccIdx] = eDccH[a];
        eDccH[a] = eDccIdx++;
    }


    boolean[] cut = new boolean[N];
    int root;

    // 无向图的割点
    void tarjanCut(int u) {
        dfn[u] = low[u] = ++timestamp;
        int son = 0;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (dfn[j] == 0) {
                tarjanCut(j);
                low[u] = Math.min(low[u], low[j]);
                if (low[j] >= dfn[u]) {
                    son++;
                    if (u != root || son > 1) {
                        cut[u] = true;
                    }
                }
            }
            // 割点判断法则为小于等于号，因此可以不用考虑父节点和重边的问题
            // 像桥那样考虑记录进入每个节点的边的编号（inEdge）也没问题
            //  else if (i != (inEdge ^ 1))
            else {
                low[u] = Math.min(low[u], dfn[j]);
            }
        }
    }


}
