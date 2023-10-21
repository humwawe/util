package template.core;

import java.util.Arrays;

/**
 * @author hum
 */
public class FenwickTree {
  private final int n;
  // 从0开始
  private final long[] table;

  public FenwickTree(int n) {
    this.n = n;
    this.table = new long[n];
  }

  public FenwickTree(int n, long x) {
    this(n);
    for (int i = 0; i < n; i++) {
      table[i] += x;
      int j = i + ((i + 1) & -(i + 1));
      if (j < n) {
        table[j] += table[i];
      }
    }
  }

  public FenwickTree(long[] a) {
    this(a.length);
    for (int i = 0; i < n; i++) {
      table[i] += a[i];
      int j = i + ((i + 1) & -(i + 1));
      if (j < n) {
        table[j] += table[i];
      }
    }
  }

  private long getSum(int i) {
    long sum = 0;
    for (; i > 0; i -= i & -i) {
      sum += table[i - 1];
    }
    return sum;
  }

  void add(int i, long x) {
    for (i++; i <= n; i += i & -i) {
      table[i - 1] += x;
    }
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

  // 二分查询最大的位置满足 前缀和 <= s
  int lowerBound(long x) {
    if (x <= 0) {
      return 0;
    }
    int pos = 0;
    for (int i = Integer.highestOneBit(n); i > 0; i /= 2) {
      if (pos + i <= n && table[pos + i - 1] < x) {
        x -= table[pos + i - 1];
        pos += i;
      }
    }
    return pos;
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
