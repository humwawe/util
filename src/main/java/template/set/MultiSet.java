package template.set;


import java.util.List;
import java.util.TreeMap;

/**
 * @author hum
 */
public class MultiSet<T> extends TreeMap<T, Long> {
  public MultiSet() {
    super();
  }

  public MultiSet(List<T> list) {
    super();
    for (T e : list) {
      this.addOne(e);
    }
  }

  public long count(Object elm) {
    return getOrDefault(elm, 0L);
  }

  public void add(T elm, long amount) {
    if (!this.containsKey(elm)) {
      put(elm, amount);
    } else {
      replace(elm, get(elm) + amount);
    }
    if (this.count(elm) == 0) {
      this.remove(elm);
    }
  }

  public void addOne(T elm) {
    this.add(elm, 1);
  }

  public void removeOne(T elm) {
    this.add(elm, -1);
  }

  public void removeAll(T elm) {
    this.add(elm, -this.count(elm));
  }

  public static <T> MultiSet<T> merge(MultiSet<T> a, MultiSet<T> b) {
    MultiSet<T> c = new MultiSet<>();
    for (T x : a.keySet()) {
      c.add(x, a.count(x));
    }
    for (T y : b.keySet()) {
      c.add(y, b.count(y));
    }
    return c;
  }
}
