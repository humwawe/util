package template.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

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

class Monoid<T> {
  static final long inf = 0x1fffffffffffffffL;
  static final int iinf = 0x3fffffff;
  static final double eps = 1e-9;
  static long mod = 1000000007;
  //  static final Monoid<Integer> SET_INTEGER = new Monoid<>((Integer) null, (a, b) -> b);
  static final Monoid<Integer> SUM_INTEGER = new Monoid<>(0, Integer::sum);
  static final Monoid<Integer> PRODUCT_INTEGER = new Monoid<>(1, (a, b) -> a * b);
  static final Monoid<Integer> XOR_INTEGER = new Monoid<>(0, (a, b) -> a ^ b);
  static final Monoid<Integer> GCD = new Monoid<>(0, (a, b) -> {
    if (b == 0) {
      return a;
    }
    while (a % b != 0) {
      int t = a % b;
      a = b;
      b = t;
    }
    return b;
  });
  static final Monoid<Integer> MIN_INTEGER = new Monoid<>(iinf, Math::min);
  static final Monoid<Integer> MAX_INTEGER = new Monoid<>(-iinf, Math::max);
  //  static final Monoid<Long> SET_LONG = new Monoid<>((Long) null, (a, b) -> b);
  static final Monoid<Long> SUM_LONG = new Monoid<>(0L, Long::sum);
  static final Monoid<Long> PRODUCT_LONG = new Monoid<>(1L, (a, b) -> a * b);
  static final Monoid<Long> XOR_LONG = new Monoid<>(0L, (a, b) -> a ^ b);
  static final Monoid<Long> MIN_LONG = new Monoid<>(inf, Math::min);
  static final Monoid<Long> MAX_LONG = new Monoid<>(-inf, Math::max);
  static final Monoid<Long> SUM_MOD = new Monoid<>(0L, (a, b) -> (a + b) % mod);
  private T identity;
  private Supplier<T> identitySupplier;
  private final BinaryOperator<T> operation;

  Monoid(BinaryOperator<T> operation) {
    this.operation = operation;
  }

  Monoid(T identity, BinaryOperator<T> operation) {
    this(operation);
    this.identity = identity;
  }

  Monoid(Supplier<T> identitySupplier, BinaryOperator<T> operation) {
    this(operation);
    this.identitySupplier = identitySupplier;
  }

  T merge(T x, T y) {
    T identity = identity();
    if (Objects.equals(x, identity)) {
      if (Objects.equals(y, identity)) {
        return identity;
      }
      return y;
    } else if (Objects.equals(y, identity)) {
      return x;
    } else {
      return operation.apply(x, y);
    }
  }

  public T identity() {
    return identitySupplier == null ? identity : identitySupplier.get();
  }

  public boolean equalsIdentity(T obj) {
    return Objects.equals(obj, identity());
  }


}