package template.queue;

import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

/**
 * @author hum
 */
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
  static final Monoid<Long> PRODUCT_MOD = new Monoid<>(1L, (a, b) -> (a * b) % mod);
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