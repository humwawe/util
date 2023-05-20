package template.set;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * @author hum
 */
public class RollbackDJSet {
  private final int[] data;
  private final int[] es;
  private final Deque<Integer> h0 = new ArrayDeque<>();
  private final Deque<Integer> h1 = new ArrayDeque<>();
  private final Deque<Integer> h2 = new ArrayDeque<>();

  public RollbackDJSet(int n) {
    data = new int[n];
    es = new int[n];
    Arrays.fill(data, -1);
  }

  int root(int x) {
    if (data[x] < 0) {
      return x;
    } else {
      return root(data[x]);
    }
  }

  boolean unite(int x, int y) {
    x = root(x);
    y = root(y);
    h0.addLast(x);
    h1.addLast(data[x]);
    h2.addLast(es[x]);
    h0.addLast(y);
    h1.addLast(data[y]);
    h2.addLast(es[y]);
    if (x == y) {
      es[x]++;
      return false;
    }
    if (data[x] > data[y]) {
      data[y] += data[x];
      data[x] = y;
      es[y] += es[x] + 1;
    } else {
      data[x] += data[y];
      data[y] = x;
      es[x] += es[y] + 1;
    }
    return true;
  }

  boolean loop(int x) {
    x = root(x);
    return es[x] >= -data[x];
  }

  boolean same(int x, int y) {
    return root(x) == root(y);
  }

  void undo() {
    int v = h0.removeLast();
    data[v] = h1.removeLast();
    es[v] = h2.removeLast();
    v = h0.removeLast();
    data[v] = h1.removeLast();
    es[v] = h2.removeLast();
  }
}
