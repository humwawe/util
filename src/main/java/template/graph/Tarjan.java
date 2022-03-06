package template.graph;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hum
 */
public class Tarjan {
  int N = 105;
  int M = 205;
  int[] dfn = new int[N];
  int[] low = new int[N];

  int timestamp;

  int[] h = new int[N];
  int[] e = new int[M];
  int[] ne = new int[M];
  int idx, top;
  int n;

  // 强连通个数
  int sccCnt = 0;
  // 某个点属于哪个强连通分量
  int[] sccC = new int[N];
  // 每个联通分量的点
  List<Integer>[] sccList = new List[N];
  int[] sccStack = new int[N];
  boolean[] inSccStack = new boolean[N];

  // 有向图的强联通分量
  void tarjanScc(int u) {
    dfn[u] = low[u] = ++timestamp;
    sccStack[++top] = u;
    inSccStack[u] = true;
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (dfn[j] == 0) {
        tarjanScc(j);
        low[u] = Math.min(low[u], low[j]);
      } else if (inSccStack[j]) {
        low[u] = Math.min(low[u], dfn[j]);
      }
    }
    // 联通分量编号递减即是拓扑序
    if (dfn[u] == low[u]) {
      ++sccCnt;
      sccList[sccCnt] = new ArrayList<>();
      int y;
      do {
        y = sccStack[top--];
        inSccStack[y] = false;
        sccC[y] = sccCnt;
        sccList[sccCnt].add(y);
      } while (y != u);
    }
  }

  // SCC缩点
  int[] sccH = new int[N];
  int[] sccE = new int[M];
  int[] sccNe = new int[M];
  int sccIdx;

  void tarjanSccReBuild() {
    Arrays.fill(sccH, -1);
    sccIdx = 0;
    // 对每条边
    for (int u = 1; u <= n; u++) {
      for (int i = h[u]; i != -1; i = ne[i]) {
        int j = e[i];
        if (sccC[u] == sccC[j]) {
          continue;
        }
        // u->j
        addScc(sccC[u], sccC[j]);
      }
    }
  }

  void addScc(int a, int b) {
    sccE[sccIdx] = b;
    sccNe[sccIdx] = sccH[a];
    sccH[a] = sccIdx++;
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
  // 某个x所属边双联通编号
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
      int x = e[i ^ 1];
      int y = e[i];
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

  // 无向图点双联通分量（v-DCC）
  // 某个编号的点双联通分量中包含的点
  List<Integer>[] vDccs = new List[N];
  // 从1开始编号
  int vDccNo;
  // 模拟栈
  int[] vDccStack = new int[N];

  // 求割点的同时，求每个点双联通分量，保存在vDccCs中
  // 若不需要求割点，则可以注释掉求cut部分
  void tarjanCutVDccNo(int u) {
    dfn[u] = low[u] = ++timestamp;
    vDccStack[++top] = u;
    // 孤立点
    if (u == root && h[u] == -1) {
      vDccNo++;
      vDccs[vDccNo] = new ArrayList<>();
      vDccs[vDccNo].add(u);
      return;
    }
    int son = 0;
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (dfn[j] == 0) {
        tarjanCutVDccNo(j);
        low[u] = Math.min(low[u], low[j]);
        if (low[j] >= dfn[u]) {
          son++;
          if (u != root || son > 1) {
            cut[u] = true;
          }
          vDccNo++;
          vDccs[vDccNo] = new ArrayList<>();
          vDccs[vDccNo].add(u);
          do {
            vDccs[vDccNo].add(vDccStack[top]);
            top--;
          } while (vDccStack[top + 1] != j);
        }
      } else {
        low[u] = Math.min(low[u], dfn[j]);
      }
    }
  }


  // v-DCC缩点
  // 把每个v-dcc和每个割点当做新图中的节点
  int[] vDccH = new int[N];
  int[] vDccE = new int[M];
  int[] vDccNe = new int[M];
  int vDccIdx;
  // 某个x所属点双联通编号
  int[] vDccC = new int[N];
  // 记录最后的总点数，联通块个数+割点个数
  int num;

  void tarjanCutVDccReBuild(int n) {
    Arrays.fill(vDccH, -1);
    vDccIdx = 0;
    // 从v-dcc编号的下一个开始
    num = vDccNo;
    // 对所有的点(1-n)
    int[] cutNewId = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      if (cut[i]) {
        cutNewId[i] = ++num;
      }
    }
    // 对每个v-dcc，在每个割点和包含它的v-dcc中连边
    for (int i = 1; i <= vDccNo; i++) {
      for (int j = 0; j < vDccs[i].size(); j++) {
        int x = vDccs[i].get(j);
        if (cut[x]) {
          addVDcc(i, cutNewId[x]);
          addVDcc(cutNewId[x], i);
        }
        // 除割点外，其他点仅属于1个v-dcc
        else {
          vDccC[x] = i;
        }
      }

    }
  }

  void addVDcc(int a, int b) {
    vDccE[vDccIdx] = b;
    vDccNe[vDccIdx] = vDccH[a];
    vDccH[a] = vDccIdx++;
  }

}
