package template.set;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author hum
 */
public class PrioritySet<T> {
  PriorityQueue<T> base, deleted;

  public PrioritySet(Comparator<? super T> comparator) {
    base = new PriorityQueue<>(comparator);
    deleted = new PriorityQueue<>(comparator);
  }

  public PrioritySet() {
    base = new PriorityQueue<>();
    deleted = new PriorityQueue<>();
  }

  void add(T x) {
    base.add(x);
  }

  void erase(T x) {
    deleted.add(x);
  }

  T peek() {
    while (!deleted.isEmpty() && deleted.peek().equals(base.peek())) {
      base.poll();
      deleted.poll();
    }
    return base.peek();
  }

  T poll() {
    while (!deleted.isEmpty() && deleted.peek().equals(base.peek())) {
      base.poll();
      deleted.poll();
    }
    return base.poll();
  }

}
