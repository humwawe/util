package template.set;

import java.util.Arrays;

/**
 * @author PF-2CRL0N
 */
public class DisjointSetRestorable {
  public int[] upper; // minus:num_element(root) plus:root(normal)
  private int[] targets;
  private int[] histupper;
  public int hp = 0;

  public DisjointSetRestorable(int n, int m) {
    upper = new int[n];
    Arrays.fill(upper, -1);

    targets = new int[2 * m];
    histupper = new int[2 * m];
    //
    //			w = new int[n];
  }

  public DisjointSetRestorable(DisjointSetRestorable ds) {
    this.upper = Arrays.copyOf(ds.upper, ds.upper.length);
    this.histupper = Arrays.copyOf(ds.histupper, ds.histupper.length);
    //
    this.hp = ds.hp;
  }

  public int root(int x) {
    return upper[x] < 0 ? x : root(upper[x]);
  }

  public boolean equiv(int x, int y) {
    return root(x) == root(y);
  }

  public boolean union(int x, int y) {
    x = root(x);
    y = root(y);
    if (x != y) {
      if (upper[y] < upper[x]) {
        int d = x;
        x = y;
        y = d;
      }
      //				w[x] += w[y];
      record(x);
      record(y);
      upper[x] += upper[y];
      //
      upper[y] = x;
    }
    return x == y;
  }

  public int time() {
    return hp;
  }

  private void record(int x) {
    targets[hp] = x;
    histupper[hp] = upper[x];
    //
    hp++;
  }

  public void revert(int to) {
    while (hp > to) {
      upper[targets[hp - 1]] = histupper[hp - 1];
      //
      hp--;
    }
  }

  public int count() {
    int ct = 0;
    for (int u : upper) {
      if (u < 0) {
        ct++;
      }
    }
    return ct;
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