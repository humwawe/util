package template.tree;

/**
 * @author hum
 */
public class SegmentTree1 {
  int N;
  int[] w;
  int[] left;
  int[] right;
  int[] val;

  public SegmentTree1(int n) {
    this.N = n + 5;
    left = new int[N * 4];
    right = new int[N * 4];
    val = new int[N * 4];
    w = new int[n];
    build(1, 0, n - 1);
  }

  public SegmentTree1(int[] w) {
    this.w = w;
    this.N = w.length + 5;
    left = new int[N * 4];
    right = new int[N * 4];
    val = new int[N * 4];
    build(1, 0, w.length - 1);
  }

  void pushUp(int u) {
    // 由子节点信息更新父节点
    val[u] = Math.max(val[u << 1], val[u << 1 | 1]);
  }

  // u是线段树的节点编号，一般从1开始，构建[l,r]的树，w[i]存每个节点的值(从l到r)
  private void build(int u, int l, int r) {
    left[u] = l;
    right[u] = r;
    if (l == r) {
      left[u] = l;
      right[u] = r;
      val[u] = w[r];
      return;
    }
    int mid = l + r >> 1;
    build(u << 1, l, mid);
    build(u << 1 | 1, mid + 1, r);
    pushUp(u);
  }

  // 从u开始查找，需满足l<=r
  int query(int u, int l, int r) {
    // 已经完全在[l,r]中了
    if (left[u] >= l && right[u] <= r) {
      return val[u];
    }
    int mid = left[u] + right[u] >> 1;
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
    if (left[u] >= l && right[u] <= r) {
      if (val[u] < d) {
        return -1;
      }
      if (left[u] == right[u]) {
        return left[u];
      }
      // 无法通过u的区间直接判断位置，考虑左和右
      if (val[u << 1] >= d) {
        return search(u << 1, l, r, d);
      } else {
        return search(u << 1 | 1, l, r, d);
      }
    }
    int mid = left[u] + right[u] >> 1;
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
    if (left[u] == x && right[u] == x) {
      // 如果是对某个点要加 v
      // val[u] += v;
      val[u] = v;
      return;
    }
    int mid = left[u] + right[u] >> 1;
    if (x <= mid) {
      modify(u << 1, x, v);
    } else {
      modify(u << 1 | 1, x, v);
    }
    pushUp(u);
  }
}
