package template.queue;

import template.set.MultiSet;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author PF-2CRL0N
 */
public class PriorityQueueBoost<T> {
  private PriorityQueue<T> queue;
  private MultiSet<T> total;
  private int size = 0;

  public PriorityQueueBoost() {
    queue = new PriorityQueue<>();
    total = new MultiSet<>();
  }

  public PriorityQueueBoost(Comparator<T> c) {
    queue = new PriorityQueue<>(c);
    total = new MultiSet<>();
  }

  public void clear() {
    queue.clear();
    total.clear();
    size = 0;
  }

  public boolean contains(T e) {
    return total.count(e) > 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public boolean offer(T e) {
    total.addOne(e);
    size++;
    return queue.offer(e);
  }

  public T peek() {
    if (total.isEmpty()) {
      return null;
    }
    simplify();
    return queue.peek();
  }

  public T poll() {
    if (total.isEmpty()) {
      return null;
    }
    simplify();
    size--;
    T res = queue.poll();
    total.removeOne(res);
    return res;
  }

  public void remove(T e) {
    total.removeOne(e);
    size--;
  }

  public int size() {
    return size;
  }

  private void simplify() {
    while (total.count(queue.peek()) == 0) {
      queue.poll();
    }
  }

}
