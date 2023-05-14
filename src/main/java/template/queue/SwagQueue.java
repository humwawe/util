package template.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * 类似队列，动态维护当前的队列中的需要值
 *
 * @author hum
 */
public class SwagQueue<T> {

  private final List<T> frontValue = new ArrayList<>();
  private final List<T> backValue = new ArrayList<>();
  private final List<T> frontSum = new ArrayList<>();
  private final List<T> backSum = new ArrayList<>();
  private int frontSize = 0;
  private int backSize = 0;
  private final Monoid<T> monoid;

  SwagQueue(Monoid<T> monoid) {
    this.monoid = monoid;
  }

  T fold() {
    if (isEmpty()) {
      return monoid.identity();
    } else if (frontSize == 0) {
      return backSum.get(backSize - 1);
    } else if (backSize == 0) {
      return frontSum.get(frontSize - 1);
    } else {
      return monoid.merge(frontSum.get(frontSize - 1), backSum.get(backSize - 1));
    }
  }

  void push(T x) {
    if (backSize == 0) {
      backValue.add(x);
      backSum.add(x);
    } else {
      T s = monoid.merge(backSum.get(backSize - 1), x);
      backValue.add(x);
      backSum.add(s);
    }
    backSize++;
  }

  T pop() {
    if (isEmpty()) {
      throw new IndexOutOfBoundsException();
    }
    if (frontSize == 0) {
      T sum = monoid.identity();
      while (backSize > 0) {
        T value = backValue.remove(backSize - 1);
        backSum.remove(backSize - 1);
        sum = monoid.merge(sum, value);
        frontValue.add(value);
        frontSum.add(sum);
        frontSize++;
        backSize--;
      }
    }
    T res = frontValue.remove(frontSize - 1);
    frontSum.remove(frontSize - 1);
    frontSize--;
    return res;
  }

  void clear() {
    frontValue.clear();
    backValue.clear();
    frontSum.clear();
    backSum.clear();
    frontSize = 0;
    backSize = 0;
  }

  boolean isEmpty() {
    return size() == 0;
  }

  int size() {
    return frontSize + backSize;
  }

  @Override
  public String toString() {
    StringJoiner joiner = new StringJoiner(", ", "[", "]");
    for (int i = frontSize - 1; i >= 0; i--) {
      joiner.add(String.valueOf(frontValue.get(i)));
    }
    for (int i = 0; i < backSize; i++) {
      joiner.add(String.valueOf(backValue.get(i)));
    }
    return joiner.toString();
  }


}

