package template.tree;

/**
 * @author hum
 */
public class TrieArray {
  /**
   * 0号点既是根节点，又是空节点
   * son[][]存储树中每个节点的子节点
   * cnt[]存储以每个节点结尾的单词数量
   */
  // 所有str的长度和
  int n = 100;
  // 有时候n太长，可以交换放到第二维
  int[][] son = new int[n][26];
  int[] cnt = new int[n];
  int idx = 0;

  void insert(String str) {
    int p = 0;
    for (int i = 0; i < str.length(); i++) {
      int u = str.charAt(i) - 'a';
      if (son[p][u] == 0) {
        son[p][u] = ++idx;
      }
      p = son[p][u];
    }
    cnt[p]++;
  }

  // 查询字符串出现的次数
  int query(String str) {
    int p = 0;
    for (int i = 0; i < str.length(); i++) {
      int u = str.charAt(i) - 'a';
      if (son[p][u] == 0) {
        return 0;
      }
      p = son[p][u];
    }
    return cnt[p];
  }
}
