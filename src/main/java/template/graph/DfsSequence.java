package template.graph;

/**
 * @author hum
 */
public class DfsSequence {
  int N = 15;
  int M = N * 2;
  int[] h = new int[N];
  int[] e = new int[M];
  int[] w = new int[M];
  int[] ne = new int[M];
  int idx;
  // 记录每个点的进入和离开时间
  int[] in = new int[N];
  int[] out = new int[N];
  int tot;

  // 子树的标号是连续的，可以把子树问题转换为区间问题
  void dfs(int u, int p) {
    in[u] = ++tot;
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (j == p) {
        continue;
      }
      dfs(j, u);
    }
    out[u] = tot;
  }

  // 点a是否为点b的祖先(包括父节点)
  // 祖先节点代表的区间刚好会包含子节点
  boolean isAncestor(int a, int b) {
    return in[a] < in[b] && out[a] >= out[b];
  }

  int[] dfn = new int[N];
  // 根据点标号如果已经有0，考虑初始化为-1
  int[] fa = new int[N];
  int timestamp = 0;
  // 存上环的点
  int[] s = new int[N];
  // 记录环上边的权值
  // int[] edge = new int[N];
  int p = 0;

  // to fix bug
  // 无向图dfs找环
  // 标定时间戳，如果访问到已经访问的点，且不是父节点，且时间戳大于当前的说明有环
  void dfsCycle(int u) {
    dfn[u] = ++timestamp;
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (dfn[j] == 0) {
        // 记录边的信息
        fa[j] = i;
        dfsCycle(j);
      }
      // 碰到环，取等于可以找自环
      //（add(x,x),add(x,x)）遍历u时，会有两条j=u，自环可能会算两边
      else if ((i ^ 1) != fa[u] && dfn[j] >= dfn[u]) {
        getCycle(u, j, w[i]);
      }
    }
  }

  void getCycle(int u, int j, int z) {
    // edge[0] = z;
    while (j != u) {
      s[p++] = j;
      // edge[p] = w[fa[j]];
      j = e[fa[j] ^ 1];
    }
    s[p++] = u;
  }

  void add(int a, int b, int c) {
    e[idx] = b;
    w[idx] = c;
    ne[idx] = h[a];
    h[a] = idx++;
  }

}