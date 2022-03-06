package template.tree;

/**
 * @author hum
 */
public class SegmentTree {
  int N = 105;
  Node[] tr = new Node[N * 4];
  int n = 55;
  int[] w = new int[n];

  void pushUp(int u) {
    // 由子节点信息更新父节点
    tr[u].v = Math.max(tr[u << 1].v, tr[u << 1 | 1].v);
  }

  // 从u开始，构建[l,r]的树，w[i]存每个节点的值(1开始)
  void build(int u, int l, int r) {
    if (l == r) {
      tr[u] = new Node(l, r, w[r]);
      return;
    }
    tr[u] = new Node(l, r);
    int mid = l + r >> 1;
    build(u << 1, l, mid);
    build(u << 1 | 1, mid + 1, r);
    pushUp(u);
  }

  // 从u开始查找
  int query(int u, int l, int r) {
    // 已经完全在[l,r]中了
    if (tr[u].l >= l && tr[u].r <= r) {
      return tr[u].v;
    }
    int mid = tr[u].l + tr[u].r >> 1;
    int v = Integer.MIN_VALUE;
    if (l <= mid) {
      v = query(u << 1, l, r);
    }
    if (r > mid) {
      v = Math.max(v, query(u << 1 | 1, l, r));
    }
    return v;
  }

  // 从u开始，修改x位置的值为v
  void modify(int u, int x, int v) {
    if (tr[u].l == x && tr[u].r == x) {
      tr[u].v = v;
      return;
    }
    int mid = tr[u].l + tr[u].r >> 1;
    if (x <= mid) {
      modify(u << 1, x, v);
    } else {
      modify(u << 1 | 1, x, v);
    }
    pushUp(u);
  }

  class Node {
    int l;
    int r;
    int v;

    public Node(int l, int r) {
      this.l = l;
      this.r = r;
    }

    public Node(int l, int r, int v) {
      this.l = l;
      this.r = r;
      this.v = v;
    }
  }

}
