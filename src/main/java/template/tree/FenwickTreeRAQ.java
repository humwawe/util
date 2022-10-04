package template.tree;

import java.util.Arrays;

/**
 * 支持单点修改，区间修改的树状数组
 * 操作时从0开始，区间操作左闭右开
 *
 * @author hum
 */
public class FenwickTreeRAQ {
  private final int n;
  private final long[] bit1;
  private final long[] bit2;

  FenwickTreeRAQ(int n) {
    this.n = n;
    this.bit1 = new long[n];
    this.bit2 = new long[n];
  }

  FenwickTreeRAQ(long[] a) {
    this(a.length);
    for (int i = 0; i < n; i++) {
      add(i, a[i]);
    }
  }

  private void add(long[] table, int i, long x) {
    for (i++; i <= n; i += i & -i) {
      table[i - 1] += x;
    }
  }

  private long getSum(long[] table, int i) {
    long sum = 0;
    for (; i > 0; i -= i & -i) {
      sum += table[i - 1];
    }
    return sum;
  }

  private long getSum(int i) {
    return getSum(bit1, i) + getSum(bit2, i) * i;
  }

  void add(int i, long x) {
    add(i, i + 1, x);
  }

  void add(int l, int r, long x) {
    add(bit1, l, -l * x);
    add(bit1, r, r * x);
    add(bit2, l, x);
    add(bit2, r, -x);
  }

  void set(int i, long x) {
    add(i, x - get(i));
  }

  long get(int i) {
    return getSum(i + 1) - getSum(i);
  }

  long getSum(int l, int r) {
    return l >= r ? 0 : getSum(r) - getSum(l);
  }

  long[] toList() {
    long[] res = new long[n];
    for (int i = 0; i < n; i++) {
      res[i] = get(i);
    }
    return res;
  }

  @Override
  public String toString() {
    return Arrays.toString(toList());
  }

}