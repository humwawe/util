package template.tree;

/**
 * @author hum
 */
public class Trie {
  /**
   * 0号点既是根节点，又是空节点
   * son[][]存储树中每个节点的子节点
   * cnt[]存储以每个节点结尾的单词数量
   */
  class Trie1 {
    // 所有str的长度和
    int n = 100;
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

  class Trie2 {
    private TrieNode root;

    public Trie2() {
      root = new TrieNode();
    }

    public void insert(String word) {
      TrieNode p = root;
      for (int i = 0; i < word.length(); i++) {
        char c = word.charAt(i);
        int index = c - 'a';
        if (p.child[index] == null) {
          TrieNode temp = new TrieNode();
          p.child[index] = temp;
          p = temp;
        } else {
          p = p.child[index];
        }
      }
      p.isEnd = true;
    }

    public boolean search(String word) {
      TrieNode p = searchNode(word);
      if (p == null) {
        return false;
      } else {
        return p.isEnd;
      }
    }

    TrieNode searchNode(String s) {
      TrieNode p = root;
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        int index = c - 'a';
        if (p.child[index] != null) {
          p = p.child[index];
        } else {
          return null;
        }
      }
      if (p == root) {
        return null;
      }
      return p;
    }
  }

  class TrieNode {
    boolean isEnd;
    TrieNode[] child;

    public TrieNode() {
      this.child = new TrieNode[26];
    }
  }

}


