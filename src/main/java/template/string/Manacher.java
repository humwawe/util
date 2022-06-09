package template.string;

/**
 * @author hum
 */
public class Manacher {
  int N = 105;
  int[] ma = new int[N * 2];
  int[] mp = new int[N * 2];

  /**
   * abaaba
   * i:     0 1 2 3 4 5 6 7 8 9 10 11 12 13
   * ma[i]: $ # a # b # a # a # b  #  a  #
   * mp[i]: 1 1 2 1 4 1 2 7 2 1 4  1  2  1
   * 遍历 区间[ 1, 2*len(str)+2 )
   * mp[i]-1 为从i向左右拓展的结果，回文串的长度
   * 当 i - (mp[i]-1) == 1 的时候，说明当前长度的串是一个前缀，同理 2*len(str)+2 - (mp[i]-1) - i == 1 是一个后缀
   */
  void manacher(char s[]) {
    int l = 0;
    ma[l++] = '$';
    ma[l++] = '#';
    for (char c : s) {
      ma[l++] = c;
      ma[l++] = '#';
    }
    mp[0] = 1;
    int mx = 0, id = 0;
    for (int i = 1; i < l; i++) {
      mp[i] = mx > i ? Math.min(mp[2 * id - i], mx - i) : 1;
      // 如果没有哨兵，比如求奇数回文串，可以直接在原串上处理，需要保证 i - mp[i] >= 0 && i + mp[i] < size
      while (ma[i + mp[i]] == ma[i - mp[i]]) {
        mp[i]++;
      }
      if (i + mp[i] > mx) {
        mx = i + mp[i];
        id = i;
      }
    }
  }

}
