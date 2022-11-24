package template.dp;

import java.util.Arrays;

/**
 * @author hum
 */
public class SosDp {
  // 基于 DP 计算高维前缀和


  // d为维度，state'为第i维恰好比state少1的点
  // for state: sum[state] = f[state];
  // for(i = 0;i <= D;i += 1)
  //   for 以字典序从小到大枚举 state
  //     sum[state] += sum[state'];

  // 以二进制的子集求和为例，超集可以求后缀和
  // a[i] 是状态i代表的子集的和
  int[] sosDp(int n, int[] a) {
    int lim = 1 << n;
    // 初始化
    int[] sum = Arrays.copyOf(a, lim);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < lim; j++) {
        if (((j >> i) & 1) == 1) {
          sum[j] += sum[j - (1 << i)];
        }
        // 超集 后缀和;
        // else {
        //   suf[j] += suf[j + (1 << i)];
        // }
      }
    }
    return sum;
  }

}
