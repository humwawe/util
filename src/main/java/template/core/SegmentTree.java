package template.core;

/**
 * @author hum
 */
public class SegmentTree {
  int N;
  int[] w;
  int[] left;
  int[] right;
  int[] max;

  public SegmentTree(int n) {
    this(n, new int[n]);
  }

  public SegmentTree(int n, int[] w) {
    this.N = w.length + 5;
    left = new int[N * 4];
    right = new int[N * 4];
    max = new int[N * 4];
    this.w = w;

    //  build(1, 0, n - 1);
  }

  void pushUp(int u) {
    max[u] = Math.max(max[u << 1], max[u << 1 | 1]);
  }

  private void build(int u, int l, int r) {
    left[u] = l;
    right[u] = r;
    if (l == r) {
      max[u] = w[r];
      return;
    }
    int mid = l + r >> 1;
    build(u << 1, l, mid);
    build(u << 1 | 1, mid + 1, r);
    pushUp(u);
  }

  int query(int u, int l, int r) {
    if (left[u] == l && right[u] == r) {
      return max[u];
    }
    int mid = left[u] + right[u] >> 1;
    if (r <= mid) {
      return query(u << 1, l, r);
    } else if (l > mid) {
      return query(u << 1 | 1, l, r);
    } else {
      return Math.max(query(u << 1, l, mid), query(u << 1 | 1, mid + 1, r));
    }

  }

  int search(int u, int l, int r, int d) {
    if (l > r) {
      return -1;
    }
    if (left[u] == l && right[u] == r) {
      if (max[u] < d) {
        return -1;
      }
      if (left[u] == right[u]) {
        return left[u];
      }
      int mid = left[u] + right[u] >> 1;
      if (max[u << 1] >= d) {
        return search(u << 1, l, mid, d);
      } else {
        return search(u << 1 | 1, mid + 1, r, d);
      }
    }
    int mid = left[u] + right[u] >> 1;
    if (r <= mid) {
      return search(u << 1, l, r, d);
    } else if (l > mid) {
      return search(u << 1 | 1, l, r, d);
    } else {
      int pos = search(u << 1, l, mid, d);
      if (pos == -1) {
        return search(u << 1 | 1, mid + 1, r, d);
      }
      return pos;
    }
  }

  void modify(int u, int x, int v) {
    if (left[u] == x && right[u] == x) {
      max[u] = v;
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