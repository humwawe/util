package template.other;

import java.util.Arrays;

/**
 * @author hum
 */
public class Mo {
  int N = 105;
  int M = 105;
  // 分块大小，一般为 sqrt(n)
  int B = 500;
  int n, m;
  int tmpAns;

  int[] mo(Node[] query) {
    // 按块号（左端点决定）排序
    // 对奇偶的块按右大或小排序
    Arrays.sort(query, (x, y) -> {
      int d = x.l / B - y.l / B;
      if (d == 0) {
        if (x.l / B % 2 == 0) {
          return x.r - y.r;
        }
        return y.r - x.r;
      }
      return d;
    });


    int[] res = new int[m];
    int l = 0;
    int r = -1;
    // 对所有询问
    for (int i = 0; i < m; i++) {
      // 扩展
      while (r < query[i].r) {
        add(++r);
      }
      while (l > query[i].l) {
        add(--l);
      }
      // 缩小
      while (r > query[i].r) {
        del(r--);
      }
      while (l < query[i].l) {
        del(l++);
      }
      res[query[i].id] = tmpAns;
    }

    return res;

  }

  private void add(int i) {
    // update tmpAns
  }

  private void del(int i) {
    // update tmpAns
  }


  class Node {
    int l;
    int r;
    int id;

    public Node(int l, int r, int id) {
      this.l = l;
      this.r = r;
      this.id = id;
    }
  }

}
