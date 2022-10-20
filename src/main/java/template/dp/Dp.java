package template.dp;

import java.util.Arrays;

/**
 * the time u need think of dp
 * a) maximum or minimum
 * b) yes or no
 * c) sum
 * can not sort/swap
 * <p>
 * four typical type
 * 1. matrix dp (10%) dp[row][col]
 * 2. sequence (40%) dp[pos]
 * 3. two sequence dp (40%) dp[posI][posJ]
 * 4. backpack (10%) dp[pos][v]
 * <p>
 * when can not solve, think about add dimension
 * if want to record the deduction process, think about add another array
 * <p>
 * key word: state, transfer function, initialization, final answer
 *
 * @author hum
 */
public class Dp {
  public int lis(long[] in) {
    int n = in.length;
    int ret = 0;
    long[] h = new long[n + 1];
    Arrays.fill(h, Long.MIN_VALUE / 2);
    for (int i = 0; i < n; i++) {
      int ind = Arrays.binarySearch(h, 0, ret + 1, in[i]);
      if (ind < 0) {
        ind = -ind - 2;
        h[ind + 1] = in[i];
        if (ind >= ret) {
          ret++;
        }
      }
    }
    return ret;
  }
}
