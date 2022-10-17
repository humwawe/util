package template.tree;

/**
 * @author hum
 * 处理区间修改的问题，不使用node结构体，避免频繁new node
 */
public class SegmentTree3 {
  int N;
  int[] left;
  int[] right;
  int[] sum;
  int[] add;
  int[] w;

  public SegmentTree3(int n) {
    N = n + 5;
    left = new int[N * 4];
    right = new int[N * 4];
    sum = new int[N * 4];
    add = new int[N * 4];
    w = new int[n];
  }

  void pushUp(int u) {
    sum[u] = sum[u << 1] + sum[u << 1 | 1];

  }

  void pushDown(int u) {
    if (add[u] != 0) {
      add[u << 1] += add[u];
      sum[u << 1] += (right[u << 1] - left[u << 1] + 1) * add[u];
      add[u << 1 | 1] += add[u];
      sum[u << 1 | 1] += (right[u << 1 | 1] - left[u << 1 | 1] + 1) * add[u];
      add[u] = 0;
    }
  }

  // 从u开始，构建[l,r]的树，w[i]存每个节点的值(从l到r)
  void build(int u, int l, int r) {
    if (l == r) {
      left[u] = l;
      right[u] = r;
      sum[u] = w[r];
      return;
    }
    left[u] = l;
    right[u] = r;
    int mid = l + r >> 1;
    build(u << 1, l, mid);
    build(u << 1 | 1, mid + 1, r);
    pushUp(u);
  }

  // 从u开始查找
  int query(int u, int l, int r) {
    // 已经完全在[l,r]中了
    if (left[u] >= l && right[u] <= r) {
      return sum[u];
    }
    pushDown(u);
    int mid = left[u] + right[u] >> 1;
    if (r <= mid) {
      return query(u << 1, l, r);
    } else if (l > mid) {
      return query(u << 1 | 1, l, r);
    } else {
      return query(u << 1, l, r) + query(u << 1 | 1, l, r);
    }

  }

  void modify(int u, int l, int r, int d) {
    if (left[u] >= l && right[u] <= r) {
      sum[u] += (right[u] - left[u] + 1) * d;
      add[u] += d;
      return;
    }
    // 分裂
    pushDown(u);

    int mid = left[u] + right[u] >> 1;
    if (r <= mid) {
      modify(u << 1, l, r, d);
    } else if (l > mid) {
      modify(u << 1 | 1, l, r, d);
    } else {
      modify(u << 1, l, r, d);
      modify(u << 1 | 1, l, r, d);
    }

    pushUp(u);
  }

}
