package template.tree;

/**
 * 基环树
 *
 * @author hum
 */
public class RingTree {
  int N = (int) (1e6 + 5);
  int M = N * 2;
  int[] h = new int[N];
  int[] e = new int[M];
  int[] ne = new int[M];
  int[] w = new int[M];
  int idx, num, p;
  int[] dfn = new int[N];
  int[] fa = new int[N];
  int[] s = new int[N];
  int[] len = new int[N];


  // 无向图找环：dfs遍历，看是否存在不是其父亲并且已经访问过的点
  private void dfs(int u) {
    dfn[u] = ++num;
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (dfn[j] == 0) {
        fa[j] = i;
        dfs(j);
      }
      // 考虑自环的话可以取到 dfn[j] >= dfn[u]
      //（add(x,x),add(x,x)）遍历u时，会有两条j=u，自环可能会算两边
      else if ((i ^ 1) != fa[u] & dfn[j] > dfn[u]) {
        getCycle(u, j, w[i]);

      }
    }
  }

  // s1, s2, ..., sp即为环上点
  // len表示两个点之间的距离
  //         s1  s2   s3  s4
  // 点：    4    3     2     1
  // 距离： 1-4   4-3   3-2   2-1
  private void getCycle(int u, int j, int z) {
    len[1] = z;
    while (j != u) {
      s[++p] = j;
      len[p + 1] = w[fa[j]];
      // 反向边 编号 fa[j]^1
      j = e[fa[j] ^ 1];
    }
    s[++p] = u;
  }

}
