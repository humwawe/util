package template.queue;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * @author hum
 */
public class SwagDeque<E> {
  private final Monoid<E> monoid;
  private Object[] val;
  private Object[] sum;
  private int head;
  private int tail;
  private int middle;

  public SwagDeque(Monoid<E> monoid) {
    this(monoid, 16);
  }

  public SwagDeque(Monoid<E> monoid, int initialCapacity) {
    this.monoid = monoid;
    this.val = new Object[initialCapacity + 1];
    this.sum = new Object[initialCapacity + 1];
  }

  private int len(int a, int b) {
    return a <= b ? b - a : b + val.length - a;
  }

  private int inc(int i) {
    return i + 1 == val.length ? 0 : i + 1;
  }

  private int dec(int i) {
    return i == 0 ? val.length - 1 : i - 1;
  }

  private int index(int j) {
    return (j += head) >= val.length ? j - val.length : j;
  }

  @SuppressWarnings("unchecked")
  private E leftSum() {
    return (E) sum[head];
  }

  @SuppressWarnings("unchecked")
  private E rightSum() {
    return (E) sum[dec(tail)];
  }

  @SuppressWarnings("unchecked")
  private void changeMid(int nmid) {
    int pmid = dec(nmid);
    int ln = len(head, nmid);
    int rn = len(nmid, tail);
    int last = val.length - 1;
    int to = dec(tail);
    if (ln > 0) {
      sum[pmid] = val[pmid];
      if (val.length - head >= ln) {
        for (int i = pmid - 1; i >= head; i--) {
          sum[i] = monoid.merge((E) val[i], (E) sum[i + 1]);
        }
      } else {
        for (int i = pmid - 1; i >= 0; i--) {
          sum[i] = monoid.merge((E) val[i], (E) sum[i + 1]);
        }
        sum[last] = monoid.merge((E) val[last], (E) sum[0]);
        for (int i = last - 1; i >= head; i--) {
          sum[i] = monoid.merge((E) val[i], (E) sum[i + 1]);
        }
      }
    }
    if (rn > 0) {
      sum[nmid] = val[nmid];
      if (to + 1 >= rn) {
        for (int i = nmid + 1; i <= to; i++) {
          sum[i] = monoid.merge((E) sum[i - 1], (E) val[i]);
        }
      } else {
        for (int i = nmid + 1; i <= last; i++) {
          sum[i] = monoid.merge((E) sum[i - 1], (E) val[i]);
        }
        sum[0] = monoid.merge((E) sum[last], (E) val[0]);
        for (int i = 1; i <= to; i++) {
          sum[i] = monoid.merge((E) sum[i - 1], (E) val[i]);
        }
      }
    }
    middle = nmid;
  }

  private void grow() {
    int oldCapacity = val.length;
    int jump = oldCapacity < 64 ? oldCapacity + 2 : oldCapacity >> 1;
    int newCapacity = oldCapacity + jump;
    val = Arrays.copyOf(val, newCapacity);
    if (tail < head || (tail == head && val[head] != null)) {
      System.arraycopy(val, head, val, head + jump, oldCapacity - head);
      int to = head + jump;
      for (int i = head; i < to; i++) {
        val[i] = null;
      }
      head = to;
    }
    sum = new Object[newCapacity];
    changeMid(index(len(head, tail) >> 1));
  }

  public void addFirst(E e) {
    E s = head == middle ? e : monoid.merge(e, leftSum());
    head = dec(head);
    val[head] = e;
    sum[head] = s;
    if (head == tail) {
      grow();
    }
  }

  public void addLast(E e) {
    val[tail] = e;
    sum[tail] = middle == tail ? e : monoid.merge(rightSum(), e);
    tail = inc(tail);
    if (head == tail) {
      grow();
    }
  }

  @SuppressWarnings("unchecked")
  public E removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    if (head == middle) {
      changeMid(index((len(head, tail) + 1) >> 1));
    }
    E e = (E) val[head];
    val[head] = null;
    sum[head] = null;
    head = inc(head);
    return e;
  }

  @SuppressWarnings("unchecked")
  public E removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    if (middle == tail) {
      changeMid(index(len(head, tail) >> 1));
    }
    tail = dec(tail);
    E e = (E) val[tail];
    val[tail] = null;
    sum[tail] = null;
    return e;
  }

  @SuppressWarnings("unchecked")
  public E getFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return (E) val[head];
  }

  @SuppressWarnings("unchecked")
  public E getLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return (E) val[dec(tail)];
  }

  @SuppressWarnings("unchecked")
  public E get(int index) {
//    Objects.checkIndex(index, size());
    return (E) val[index(index)];
  }

  public int size() {
    return len(head, tail);
  }

  public boolean isEmpty() {
    return head == tail;
  }

  public E fold() {
    if (head == tail) {
      return monoid.identity();
    } else if (head == middle) {
      return rightSum();
    } else if (middle == tail) {
      return leftSum();
    } else {
      return monoid.merge(leftSum(), rightSum());
    }
  }

  public void clear() {
    head = 0;
    middle = 0;
    tail = 0;
    for (int i = 0; i < val.length; i++) {
      val[i] = null;
      sum[i] = null;
    }
  }
}