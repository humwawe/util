package template.core;

/**
 * 持久化权值线段树，查询区间第k大
 *
 * @author hum
 */
public class SegmentPersistenceTree {
  int N;
  int[] lch;
  int[] rch;
  int[] cnt;
  int idx;

  public SegmentPersistenceTree(int n) {
    N = n + 5;
    // N*4+M*logN
    lch = new int[N * 20];
    rch = new int[N * 20];
    cnt = new int[N * 20];
    idx = 0;
    // build(0, n - 1);
  }

  public int build(int l, int r) {
    int p = ++idx;
    if (l == r) {
      return p;
    }
    int mid = l + r >> 1;
    lch[p] = build(l, mid);
    rch[p] = build(mid + 1, r);

    return p;
  }

  // insert(root[i],0,n-1,x)
  public int insert(int pre, int l, int r, int x) {
    int q = ++idx;
    copy(pre, q);

    if (l == r) {
      cnt[q]++;
      return q;
    }

    int mid = l + r >> 1;
    if (x <= mid) {
      lch[q] = insert(lch[pre], l, mid, x);
    } else {
      rch[q] = insert(rch[pre], mid + 1, r, x);
    }

    pushUp(q);
    return q;
  }


  public int query(int cur, int pre, int l, int r, int k) {
    if (l == r) {
      return l;
    }

    int mid = l + r >> 1;
    int c = cnt[lch[cur]] - cnt[lch[pre]];

    if (k <= c) {
      return query(lch[cur], lch[pre], l, mid, k);
    } else {
      return query(rch[cur], rch[pre], mid + 1, r, k - c);
    }

  }

  private void copy(int p, int q) {
    lch[q] = lch[p];
    rch[q] = rch[p];
    cnt[q] = cnt[p];
  }

  private void pushUp(int u) {
    cnt[u] = cnt[lch[u]] + cnt[rch[u]];
  }

}
