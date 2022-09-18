package template.tree;

/**
 * @author hum
 */
public class SegmentTree {
  int N;
  Node[] tr;
  int[] w;

  public SegmentTree(int n) {
    w = new int[n];
    this.N = n + 5;
    tr = new Node[N * 4];
  }

  public SegmentTree(int[] w) {
    this.w = w;
    this.N = w.length + 5;
    tr = new Node[N * 4];
  }

  void pushUp(int u) {
    // 由子节点信息更新父节点
    tr[u].v = Math.max(tr[u << 1].v, tr[u << 1 | 1].v);
  }

  // u是线段树的节点编号，一般从1开始，构建[l,r]的树，w[i]存每个节点的值(从l到r)
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
    if (r <= mid) {
      return query(u << 1, l, r);
    } else if (l > mid) {
      return query(u << 1 | 1, l, r);
    } else {
      return Math.max(query(u << 1, l, r), query(u << 1 | 1, l, r));
    }
  }

  // 线段树上二分，找区间内第一个大于等于d的位置
  // 没找到返回 -1
  int search(int u, int l, int r, int d) {
    // 已经完全在[l,r]中了
    if (tr[u].l >= l && tr[u].r <= r) {
      if (tr[u].v < d) {
        return -1;
      }
      if (tr[u].l == tr[u].r) {
        return tr[u].l;
      }
      // 无法通过u的区间直接判断位置，考虑左和右
      if (tr[u << 1].v >= d) {
        return search(u << 1, l, r, d);
      } else {
        return search(u << 1 | 1, l, r, d);
      }
    }
    int mid = tr[u].l + tr[u].r >> 1;
    if (r <= mid) {
      return search(u << 1, l, r, d);
    } else if (l > mid) {
      return search(u << 1 | 1, l, r, d);
    } else {
      int pos = search(u << 1, l, r, d);
      if (pos == -1) {
        return search(u << 1 | 1, l, r, d);
      }
      return pos;
    }
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
