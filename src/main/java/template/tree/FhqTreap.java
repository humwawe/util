package template.tree;

import java.util.Random;

/**
 * @author hum
 */
public class FhqTreap {
  int N;
  int inf = 0x3f3f3f3f;
  Random random;
  int[] size, val, rnd;
  int[][] son;
  int idx;
  int root;


  public FhqTreap(int n) {
    N = n + 5;
    random = new Random();
    size = new int[N];
    val = new int[N];
    rnd = new int[N];
    son = new int[N][2];
  }


  int newNode(int x) {
    val[++idx] = x;
    rnd[idx] = random.nextInt(inf);
    size[idx] = 1;
    return idx;
  }

  void pushup(int x) {
    size[x] = size[son[x][1]] + size[son[x][0]] + 1;
  }

  // 分裂成小于等于v和大于v的两部分
  int[] split(int x, int v) {
    if (x == 0) {
      return new int[]{0, 0};
    }
    if (val[x] <= v) {
      int[] p = split(son[x][1], v);
      son[x][1] = p[0];
      pushup(x);
      return new int[]{x, p[1]};
    } else {
      int[] p = split(son[x][0], v);
      son[x][0] = p[1];
      pushup(x);
      return new int[]{p[0], x};
    }
  }


  int merge(int x, int y) {
    if (x == 0 || y == 0) {
      return x | y;
    }
    if (rnd[x] < rnd[y]) {
      son[x][1] = merge(son[x][1], y);
      pushup(x);
      return x;
    } else {
      son[y][0] = merge(x, son[y][0]);
      pushup(y);
      return y;
    }
  }

  public void insert(int v) {
    int[] p = split(root, v);
    root = merge(merge(p[0], newNode(v)), p[1]);
  }

  public void delete(int v) {
    int[] p = split(root, v);
    int[] q = split(p[0], v - 1);
    q[1] = merge(son[q[1]][0], son[q[1]][1]);
    root = merge(merge(q[0], q[1]), p[1]);
  }

  public int rank(int v) {
    int[] p = split(root, v - 1);
    int res = size[p[0]] + 1;
    root = merge(p[0], p[1]);
    return res;
  }

  public int kth(int k) {
    return kth(root, k);
  }


  private int kth(int x, int k) {
    if (size[son[x][0]] == k - 1) {
      return val[x];
    }
    if (size[son[x][0]] < k - 1) {
      return kth(son[x][1], k - size[son[x][0]] - 1);
    } else {
      return kth(son[x][0], k);
    }
  }

  public int prev(int v) {
    int[] p = split(root, v - 1);
    int x = p[0];
    while (son[x][1] != 0) {
      x = son[x][1];
    }
    root = merge(p[0], p[1]);
    return val[x];
  }

  public int next(int v) {
    int[] p = split(root, v);
    int x = p[1];
    while (son[x][0] != 0) {
      x = son[x][0];
    }
    root = merge(p[0], p[1]);
    return val[x];
  }

}
