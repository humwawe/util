package template.dp;

/**
 * @author PF-2CRL0N
 */
public class NumDp {
  int[] num;
  Long[][] memo;
  int len;

  public NumDp(int x) {
    int[] tmp = new int[15];
    len = 0;
    while (x > 0) {
      tmp[len++] = x % 10;
      x /= 10;
    }
    num = new int[len];
    for (int i = 0; i < len; i++) {
      num[i] = tmp[len - 1 - i];
    }
    memo = new Long[len][1 << len];
  }

  public long getRes() {
    return dfs(0, 0, true, true);
  }

  // dfs(0, 0, true, true);
  long dfs(int i, int st, boolean limit, boolean lead) {
    // 算到num的最后一位
    if (i == num.length) {
      // 考虑能否取 0
      return lead ? 0 : 1;
    }
    if (!limit && !lead && memo[i][st] != null) {
      return memo[i][st];
    }

    long res = 0;
    // 可以跳过当前数位
    if (lead) {
      res = dfs(i + 1, st, false, true);
    }

    int up = limit ? num[i] : 9;
    // 根据前导0判断是否能取到0
    int low = lead ? 1 : 0;

    for (int j = low; j <= up; j++) {
      // 处理某种条件
      if ((st >> j & 1) == 0) {
        res += dfs(i + 1, st | (1 << j), limit && j == up, false);
      }
    }
    if (!limit && !lead) {
      memo[i][st] = res;
    }
    return res;
  }
}
