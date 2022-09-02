package template.graph;

/**
 * @author hum
 */
// 重链剖分，儿子节点多的边叫重边，否则为轻边
// 树上一条路径的包含重链段和轻链边，轻链个数量级小于log(n)，维护每个重链段的头结点，保证复杂度
// 把路径和子树问题转换为多个区间的问题，一般可以结合线段树来使用
public class Decomposition {

  int N = 100010;
  int M = N * 2;
  int[] h = new int[N];
  int[] e = new int[M];
  int[] ne = new int[M];
  int idx;

  int[] dep = new int[N];
  int[] fa = new int[N];

  // dfs序
  int[] in = new int[N];
  int[] out = new int[N];
  // dfs序编号，对应的哪个点
  int[] id = new int[N];
  int tot;

  // 记录子树大小，判断哪个是重儿子
  int[] size = new int[N];
  // 某个点的重儿子的编号，没有儿子节点为 -1
  int[] hs = new int[N];

  int[] top = new int[N];

  // dfs1(1,1)
  // 第一遍求子树大小，重儿子，父节点，深度
  void dfs1(int u, int p) {
    size[u] = 1;
    hs[u] = -1;
    fa[u] = p;
    dep[u] = dep[p] + 1;
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (j == p) {
        continue;
      }
      dfs1(j, u);
      size[u] += size[j];
      // 更新重儿子
      if (hs[u] == -1 || size[j] > size[hs[u]]) {
        hs[u] = j;
      }
    }
  }


  // dfs2(1,1)
  // 第二遍，按重链优先求dfs序（重链编号连续、子树编号连续），重链链头节点
  // t表示重链的头结点
  void dfs2(int u, int t) {
    top[u] = t;
    in[u] = ++tot;
    id[tot] = u;
    if (hs[u] != -1) {
      dfs2(u, t);
    }
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (j == fa[u] || j == hs[u]) {
        continue;
      }
      dfs2(j, j);

    }

    out[u] = tot;
  }


  // 求 a 和 b 的lca，那个点所在链头更深就往上跳直到到达同一个链
  int lca(int a, int b) {
    while (top[a] != top[b]) {
      if (dep[top[a]] < dep[top[b]]) {
        b = fa[top[b]];
      } else {
        a = fa[top[a]];
      }
    }
    // 返回一条链上深度更低的
    if (dep[a] < dep[b]) {
      return a;
    }
    return b;
  }
}
