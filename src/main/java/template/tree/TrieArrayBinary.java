package template.tree;

import java.util.Arrays;

/**
 * form uwi
 *
 * @author hum
 */
public class TrieArrayBinary {
  // root = 0
  public L next;
  public int gen;
  public int W;

  public TrieArrayBinary(int W) {
    this.W = W;
    this.next = new L(2);
    this.gen = 1;
    this.next.add(-1).add(-1);
  }

  public void add(long s) {
    int cur = 0;
    for (int d = W - 1; d >= 0; d--) {
      int v = (int) (s >>> d & 1);
      int nex = next.a[cur * 2 + v];
      if (nex == -1) {
        nex = next.a[cur * 2 + v] = gen++;
        next.add(-1).add(-1);
      }
      cur = nex;
    }
  }

  public long xormax(long x) {
    if (gen == 1) {
      return 0;
    }
    int cur = 0;
    long ret = 0;
    for (int d = W - 1; d >= 0; d--) {
      if (cur == -1) {
        ret |= x << -d >>> -d;
        break;
      }
      int xd = (int) (x >>> d & 1);
      if (next.a[cur * 2 | xd ^ 1] != -1) {
        ret |= 1L << d;
        cur = next.a[cur * 2 | xd ^ 1];
      } else {
        cur = next.a[cur * 2 | xd];
      }
    }
    return ret;
  }

  public int[] des() {
    int[] des = new int[gen];
    for (int i = gen - 1; i >= 0; i--) {
      if (next.a[2 * i] != -1) {
        des[i] += des[next.a[2 * i]];
      }
      if (next.a[2 * i + 1] != -1) {
        des[i] += des[next.a[2 * i + 1]];
      }
      if (des[i] == 0) {
        des[i] = 1;
      }
    }
    return des;
  }

  public int mex(long x, int[] des) {
    int ret = 0;
    for (int cur = 0, d = W - 1; d >= 0 && cur != -1; d--) {
      int xd = (int) (x >>> d & 1);
      if (next.a[2 * cur | xd] != -1 && des[next.a[2 * cur | xd]] == 1 << d) {
        ret |= 1 << d;
        cur = next.a[2 * cur | xd ^ 1];
      } else {
        cur = next.a[2 * cur | xd];
      }
    }
    return ret;
  }

  public boolean contains(long x, int low) {
    int cur = 0;
    for (int d = W - 1; d >= low; d--) {
      int v = (int) (x >>> d & 1);
      int nex = next.a[cur * 2 + v];
      if (nex == -1) {
        return false;
      }
    }
    return true;
  }

  class L {
    public int[] a;
    public int p = 0;

    public L(int n) {
      a = new int[n];
    }

    public L add(int n) {
      if (p >= a.length) {
        a = Arrays.copyOf(a, a.length * 3 / 2 + 1);
      }
      a[p++] = n;
      return this;
    }

    public int size() {
      return p;
    }

    public L clear() {
      p = 0;
      return this;
    }

    public int[] toArray() {
      return Arrays.copyOf(a, p);
    }

    @Override
    public String toString() {
      return Arrays.toString(toArray());
    }
  }

}
