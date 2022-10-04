package template.tree;

import java.util.Arrays;

/**
 * 操作时从0开始，区间操作左闭右开
 *
 * @author hum
 */
public class SegmentTreeRMQ {
  public final int M, H, N;
  public int[] vals;
  public final int I = Integer.MAX_VALUE;

  // min, max可以考虑存赋值
  public SegmentTreeRMQ(int n) {
    N = n;
    M = Integer.highestOneBit(Math.max(N - 1, 1)) << 2;
    H = M >>> 1;
    vals = new int[M];
    Arrays.fill(vals, 0, M, I);
  }

  public SegmentTreeRMQ(int[] a) {
    this(a.length);
    for (int i = 0; i < N; i++) {
      vals[H + i] = a[i];
    }
    //		Arrays.fill(vals, H+N, M, I);
    for (int i = H - 1; i >= 1; i--) {
      propagate(i);
    }
  }

  public void update(int pos, int x) {
    vals[H + pos] = x;
    for (int i = (H + pos) >>> 1; i >= 1; i >>>= 1) {
      propagate(i);
    }
  }

  private void propagate(int i) {
    vals[i] = Math.min(vals[2 * i], vals[2 * i + 1]);
  }

  public int min(int l, int r) {
    int min = I;
    if (l >= r) {
      return min;
    }
    l += H;
    r += H;
    for (; l < r; l >>>= 1, r >>>= 1) {
      if ((l & 1) == 1) {
        min = Math.min(min, vals[l++]);
      }
      if ((r & 1) == 1) {
        min = Math.min(min, vals[--r]);
      }
    }
    return min;
  }

  // 从l开始第一个小于等于v的位置
  public int firstle(int l, int v) {
    if (l >= H) {
      return -1;
    }
    int cur = H + l;
    while (true) {
      if (vals[cur] <= v) {
        if (cur >= H) {
          return cur - H;
        }
        cur = 2 * cur;
      } else {
        cur++;
        if ((cur & cur - 1) == 0) {
          return -1;
        }
        if ((cur & 1) == 0) {
          cur >>>= 1;
        }
      }
    }
  }

  // 从r往前第一个小于等于v的位置
  public int lastle(int r, int v) {
    if (r < 0) {
      return -1;
    }
    int cur = H + r;
    while (true) {
      if (vals[cur] <= v) {
        if (cur >= H) {
          return cur - H;
        }
        cur = 2 * cur + 1;
      } else {
        if ((cur & cur - 1) == 0) {
          return -1;
        }
        cur--;
        if ((cur & 1) == 1) {
          cur >>>= 1;
        }
      }
    }
  }

}

