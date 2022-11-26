package template.core;

/**
 * @author hum
 */
public class BitOp {

  // x 的最后一个 1 代表的值
  // 1010 -> 10 -> 2
  int lowbit(int x) {
    return x & -x;
    //  return Integer.lowestOneBit(x);
  }

  // x 的第一个 1 代表的值
  // 1010 -> 1000 -> 8
  int highbit(int x) {
    return Integer.highestOneBit(x);
  }

  boolean isPow2(int x) {
    return x > 0 && x == lowbit(x);
  }

  public static void main(String[] args) {
    BitOp bitOp = new BitOp();
    System.out.println(Integer.highestOneBit(3));
  }
}
