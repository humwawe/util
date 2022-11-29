package template.core;

/**
 * @author hum
 */
public class BitOp {

  // x 的最后一个 1 代表的值
  // 1010 -> 10 -> 2
  public int lowbit(int x) {
    return x & -x;
    //  return Integer.lowestOneBit(x);
  }

  // x 的第一个 1 代表的值
  // 1010 -> 1000 -> 8
  public int highbit(int x) {
    return Integer.highestOneBit(x);
  }

  public boolean isPow2(int x) {
    return x > 0 && x == lowbit(x);
  }

}
