package template.tree;

/**
 * @author hum
 */
public class TriePoint {


  private TrieNode root;

  public TriePoint() {
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

  class TrieNode {
    boolean isEnd;
    TrieNode[] child;

    public TrieNode() {
      this.child = new TrieNode[26];
    }
  }

}


