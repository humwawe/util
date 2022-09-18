package template.graph;

/**
 * @author hum
 */
// 本质：启发式合并，轻儿子往重儿子合并
public class DsuOnTree {

  int N = 100010;
  int M = N * 2;
  int[] h = new int[N];
  int[] e = new int[M];
  int[] ne = new int[M];
  int idx;
  // dfs序用来快速遍历子树
  int[] in = new int[N];
  int[] out = new int[N];
  // dfs序编号，对应的哪个点
  int[] id = new int[N];
  int tot;
  // 记录子树大小，判断哪个是重儿子
  int[] size = new int[N];
  // 某个点的重儿子的编号，没有儿子节点为 -1
  int[] hs = new int[N];


  // dfsInit(1,1)
  void dfsInit(int u, int p) {
    in[u] = ++tot;
    id[tot] = u;
    size[u] = 1;
    hs[u] = -1;
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (j == p) {
        continue;
      }
      dfsInit(j, u);
      size[u] += size[j];
      // 更新重儿子
      if (hs[u] == -1 || size[j] > size[hs[u]]) {
        hs[u] = j;
      }
    }
    out[u] = tot;
  }

  // dfsSovle(1,1,false)
  // keep 表示是否保留信息
  void dfsSovle(int u, int p, boolean keep) {

    // 先递归算轻儿子
    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (j == p || j == hs[u]) {
        continue;
      }
      dfsSovle(j, u, false);
    }

    // 再算重儿子
    if (hs[u] != -1) {
      dfsSovle(hs[u], u, true);
    }

    for (int i = h[u]; i != -1; i = ne[i]) {
      int j = e[i];
      if (j == p || j == hs[u]) {
        continue;
      }
      // j 是轻儿子，把 j 子树里所有节点加入到重儿子集合中
      for (int x = in[j]; x <= out[j]; x++) {
        add(id[x]);
      }
    }

    // u 本身加入
    add(u);

    /*
     * 一般在此 update(ans)
     */

    // 轻儿子清空临时使用的空间
    if (!keep) {
      for (int x = in[u]; x <= out[u]; x++) {
        del(id[x]);
      }
    }

  }


  private void add(int i) {
    // update tmpAns
  }

  private void del(int i) {
    // update tmpAns
  }

}
