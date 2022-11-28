package template.tree;

/**
 * @author hum
 * 处理区间修改的问题，不使用node结构体，避免频繁new node
 */
public class SegmentTree3 {
  int N;
  int[] left;
  int[] right;
  long[] sum;
  long[] add;
  int[] w;

  public SegmentTree3(int n) {
    N = n + 5;
    left = new int[N * 4];
    right = new int[N * 4];
    sum = new long[N * 4];
    add = new long[N * 4];
    w = new int[n];
  }

  void pushUp(int u) {
    pushDown(u << 1);
    pushDown(u << 1 | 1);
    sum[u] = sum[u << 1] + sum[u << 1 | 1];

  }

  void pushDown(int u) {
    // 有tag
    if (add[u] != 0) {
      // calLazy(u): 计算当前tag的当前层的影响
      sum[u] = (right[u] - left[u] + 1) * add[u];
      // unionLazy: 有儿子节点，向下传递tag
      // tag对儿子层的影响未计算，每次查询时候发现有tag需要先pushDown
      if (left[u] != right[u]) {
        add[u << 1] += add[u];
        add[u << 1 | 1] += add[u];
      }
      // initLazy(u): tag恢复
      add[u] = 0;
    }
  }

  // 从u开始，构建[l,r]的树，w[i]存每个节点的值(从l到r)
  void build(int u, int l, int r) {
    left[u] = l;
    right[u] = r;
    if (l == r) {
      sum[u] = w[r];
      return;
    }

    int mid = l + r >> 1;
    build(u << 1, l, mid);
    build(u << 1 | 1, mid + 1, r);
    pushUp(u);
  }

  // 从u开始查找
  long query(int u, int l, int r) {
    pushDown(u);

    if (left[u] == l && right[u] == r) {
      return sum[u];
    }

    int mid = left[u] + right[u] >> 1;
    if (r <= mid) {
      return query(u << 1, l, r);
    } else if (l > mid) {
      return query(u << 1 | 1, l, r);
    } else {
      // merge(query(u << 1, l, r), query(u << 1 | 1, l, r))
      return query(u << 1, l, mid) + query(u << 1 | 1, mid + 1, r);
    }

  }

  void add(int u, int l, int r, int d) {
    pushDown(u);

    if (left[u] == l && right[u] == r) {
      add[u] = d;
      return;
    }

    int mid = left[u] + right[u] >> 1;
    if (r <= mid) {
      add(u << 1, l, r, d);
    } else if (l > mid) {
      add(u << 1 | 1, l, r, d);
    } else {
      add(u << 1, l, mid, d);
      add(u << 1 | 1, mid + 1, r, d);
    }

    pushUp(u);
  }

}
