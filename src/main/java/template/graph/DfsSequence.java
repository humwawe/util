package template.graph;

/**
 * @author hum
 */
public class DfsSequence {
  int N = 15;
  Graph graph;
  // 记录每个点的进入和离开时间
  int[] in = new int[N];
  int[] out = new int[N];
  int tot;

  // 子树的标号是连续的，可以把子树问题转换为区间问题
  void dfs(int u, int p) {
    in[u] = ++tot;
    for (int i = graph.h[u]; i != -1; i = graph.ne[i]) {
      int j = graph.e[i];
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


}