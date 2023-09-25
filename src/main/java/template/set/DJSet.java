package template.set;

/**
 * @author hum
 */
public class DJSet {
  // 负数表示集合的大小，正数表示属于这个集合的代表元素
  public int[] upper;
  private final int[] prev;

  public DJSet(int n) {
    upper = new int[n];
    prev = new int[n];
    for (int i = 0; i < n; i++) {
      upper[i] = -1;
      prev[i] = i - 1;
    }
  }


  public int root(int x) {
    return upper[x] < 0 ? x : (upper[x] = root(upper[x]));
  }

  public boolean equiv(int x, int y) {
    return root(x) == root(y);
  }

  public boolean unite(int x, int y) {
    x = root(x);
    y = root(y);
    // 按集合大小合并，否则优先合并到x
    if (x != y) {
      if (upper[y] < upper[x]) {
        int d = x;
        x = y;
        y = d;
      }
      upper[x] += upper[y];
      upper[y] = x;
    }
    return x == y;
  }

  // 查询[l,r] 是否在同一个集合
  boolean query(int l, int r) {
    assert l <= r;
    int root = root(r);
    int rr = r;
    while (0 <= rr && root(rr) == root) {
      rr = prev[rr];
    }
    int i = r;
    while (0 <= i && root(i) == root) {
      int temp = prev[i];
      prev[i] = rr;
      i = temp;
    }
    return rr < l;
  }

  // 集合个数
  public int count() {
    int ct = 0;
    for (int u : upper) {
      if (u < 0) {
        ct++;
      }
    }
    return ct;
  }

  // x 所在集合大小
  public int size(int x) {
    return -upper[root(x)];
  }

  public int[][] toBucket() {
    int n = upper.length;
    int[][] ret = new int[n][];
    int[] rp = new int[n];
    for (int i = 0; i < n; i++) {
      if (upper[i] < 0) {
        ret[i] = new int[-upper[i]];
      }
    }
    for (int i = 0; i < n; i++) {
      int r = root(i);
      ret[r][rp[r]++] = i;
    }
    return ret;
  }

}
