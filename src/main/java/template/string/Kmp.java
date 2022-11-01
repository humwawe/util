package template.string;

/**
 * @author hum
 */
public class Kmp {

  /**
   * next[] 的含义：x[i-next[i]...i-1]=x[0...next[i]-1]
   * next[i] 为满足 x[i-z...i-1]=x[0...z-1] 的最大 z 值（就是 x 的自身匹配）
   */
  void kmpPre(char x[], int m, int[] next) {
    int i, j;
    j = next[0] = -1;
    i = 0;
    while (i < m) {
      while (-1 != j && x[i] != x[j]) {
        j = next[j];
      }
      next[++i] = ++j;
    }
  }

  /**
   * kmpNext[i] 的意思:next'[i]=next[next[...[next[i]]]](直到 next'[i]<0 或者 x[next'[i]]!=x[i])
   */
  void kmpPre2(char x[], int m, int[] kmpNext) {
    int i, j;
    j = kmpNext[0] = -1;
    i = 0;
    while (i < m) {
      while (-1 != j && x[i] != x[j]) {
        j = kmpNext[j];
      }
      if (x[++i] == x[++j]) {
        kmpNext[i] = kmpNext[j];
      } else {
        kmpNext[i] = j;
      }
    }
  }

  // y[]是长文本，x[]是模式串
  public int kmp(char[] y, char[] x) {
    int n = y.length;
    int m = x.length;

    int[] next = new int[m + 1];
    int i, j;
    int ans = 0;
    kmpPre(x, m, next);
    i = j = 0;
    while (i < n) {
      while (-1 != j && y[i] != x[j]) {
        j = next[j];
      }
      i++;
      j++;
      if (j >= m) {
        // 匹配成功
        System.out.println(i);
        ans++;
        j = next[j];
      }
    }
    return ans;
  }


  /**
   * Z函数，扩展kmp
   * z(aaaaa) = [0,4,3,2,1]
   * z(aaabaab) = [0,2,1,0,2,1,0]
   * z(abacaba) = [0,0,1,0,3,0,1]
   *
   * @param s 下标从0开始
   * @return z[i] 表示 s[0] 和 s[i] 开头的最长公共前缀（lcp）长度，z[0]=0
   */
  int[] zFunction(String s) {
    int n = s.length();
    int[] z = new int[n];
    for (int i = 1, l = 0, r = 0; i < n; ++i) {
      if (i <= r && z[i - l] < r - i + 1) {
        z[i] = z[i - l];
      } else {
        z[i] = Math.max(0, r - i + 1);
        while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
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
