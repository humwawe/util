package template.core;

import java.util.Arrays;

/**
 * @author hum
 */
public class Graph {
  public int N, M;
  public int[] h;
  public int[] e;
  public int[] ne;
  public int[] w;
  int idx, n, m;
  // 是否有方向，默认无向边
  boolean flag;


  public Graph(int n, int m) {
    this(n, m, false);
  }

  public Graph(int n, int m, boolean f) {
    this.n = n;
    this.m = m;
    flag = f;
    N = n + 5;
    M = flag ? m + 5 : m * 2 + 5;

    h = new int[N];
    e = new int[M];
    ne = new int[M];
    w = new int[M];
    idx = 0;
    Arrays.fill(h, -1);
  }

  public void add(int a, int b) {
    add0(a, b);
    if (!flag) {
      add0(b, a);
    }
  }

  public void add(int a, int b, int c) {
    add0(a, b, c);
    if (!flag) {
      add0(b, a, c);
    }
  }

  public void add0(int a, int b) {
    e[idx] = b;
    ne[idx] = h[a];
    h[a] = idx++;
  }

  public void add0(int a, int b, int c) {
    e[idx] = b;
    w[idx] = c;
    ne[idx] = h[a];
    h[a] = idx++;
  }

}
