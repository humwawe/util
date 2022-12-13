package template.core;

import java.util.Arrays;

/**
 * @author hum
 */
public class StringMatch {
  //[0..next[i]-1] == [i-next[i]+1..i]
  public static int[] kmpNext(char[] s) {
    int n = s.length;
    int[] next = new int[n];
    for (int i = 1; i < n; i++) {
      next[i] = next[i - 1];
      while (next[i] > 0 && s[i] != s[next[i]]) {
        next[i] = next[next[i] - 1];
      }
      next[i] += s[i] == s[next[i]] ? 1 : 0;
    }
    return next;
  }

  public static int[] kmpMatch(char[] s, char[] t) {
    int n = s.length;
    int m = t.length;
    int[] res = new int[n];
    int cnt = 0;

    int[] next = kmpNext(t);
    int j = 0;
    for (int i = 0; i < n; ) {
      while (j > 0 && s[i] != t[j]) {
        j = next[j - 1];
      }
      if (s[i] == t[j]) {
        i++;
        j++;
      } else {
        i++;
      }
      if (j == m) {
        res[cnt++] = i - m;
        j = next[m - 1];
      }
    }
    return Arrays.copyOf(res, cnt);
  }


  // 循环周期
  public static int[] periodic(char[] s) {
    int n = s.length;
    int[] next = kmpNext(s);
    int[] res = new int[n];
    int cnt = 0;
    int len = n;
    while (len > 0) {
      len = next[len - 1];
      res[cnt++] = n - len;
    }
    return Arrays.copyOf(res, cnt);
  }


  public static int[] zFunction(char[] s) {
    int n = s.length;
    int[] z = new int[n];
    for (int i = 1, l = 0, r = 0; i < n; ++i) {
      if (i <= r && z[i - l] < r - i + 1) {
        z[i] = z[i - l];
      } else {
        z[i] = Math.max(0, r - i + 1);
        while (i + z[i] < n && s[z[i]] == s[i + z[i]]) {
          ++z[i];
        }
      }
      if (i + z[i] - 1 > r) {
        l = i;
        r = i + z[i] - 1;
      }
    }
    return z;
  }
}
