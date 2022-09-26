package template.tree;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * from uwi
 *
 * @author PF-2CRL0N
 */
public class TrieByList {
  public Node root = new Node((char) 0, 0);
  public int gen = 1;

  public static class Node {
    public int id;
    public char c;

    public long ptn = 0;
    public int p = 0;
    public Node[] child = null;
    public int hit = -1;

    public Node fail;

    public Node(char c, int id) {
      this.id = id;
      this.c = c;
    }

    public int enc(char c) {
      //			return c <= 'Z' ? c-'A' : c-'a'+32;
      return c - 'a';
    }

    public void appendChild(Node n) {
      if (p == 0) {
        child = new Node[1];
      } else if (p + 1 >= child.length) {
        child = Arrays.copyOf(child, child.length * 2);
      }
      int z = enc(n.c);
      int nind = Long.bitCount(ptn << ~z);
      ptn |= 1L << z;
      System.arraycopy(child, nind, child, nind + 1, p - nind);
      child[nind] = n;
      p++;
    }

    public Node search(char c) {
      if (ptn << ~enc(c) < 0) {
        return child[Long.bitCount(ptn << ~enc(c)) - 1];
      } else {
        return null;
      }
    }

    public String toString(String indent) {
      StringBuilder sb = new StringBuilder();
      sb.append(indent + id + ":" + c);
      if (hit != 0) {
        sb.append(" H:" + hit);
      }
      if (fail != null) {
        sb.append(" F:" + fail.id);
      }
      sb.append("\n");
      for (int i = 0; i < p; i++) {
        sb.append(child[i].toString(indent + "  "));
      }
      return sb.toString();
    }
  }

  public void add(char[] s, int id) {
    Node cur = root;
    Node pre = null;
    for (char c : s) {
      pre = cur;
      cur = pre.search(c);
      if (cur == null) {
        cur = new Node(c, gen++);
        pre.appendChild(cur);
      }
    }
    cur.hit = id;
  }

  public void buildFailure() {
    root.fail = null;
    Queue<Node> q = new ArrayDeque<Node>();
    q.add(root);
    while (!q.isEmpty()) {
      Node cur = q.poll();
      inner:
      for (int i = 0; i < cur.p; i++) {
        Node ch = cur.child[i];
        q.add(ch);
        for (Node to = cur.fail; to != null; to = to.fail) {
          Node lch = to.search(ch.c);
          if (lch != null) {
            ch.fail = lch;
            ch.hit += lch.hit; // propagation of hit
            continue inner;
          }
        }
        ch.fail = root;
      }
    }
  }

  public Node next(Node cur, char c) {
    for (; cur != null; cur = cur.fail) {
      Node next = cur.search(c);
      if (next != null) {
        return next;
      }
      // dead
    }
    return root;
  }

  public int countHit(char[] q) {
    Node cur = root;
    int hit = 0;
    outer:
    for (char c : q) {
      for (; cur != null; cur = cur.fail) {
        Node next = cur.search(c);
        if (next != null) {
          hit += next.hit;
          cur = next;
          continue outer;
        }
      }
      cur = root;
    }
    return hit;
  }

  public Node[] toArray() {
    Node[] ret = new Node[gen];
    ret[0] = root;
    for (int i = 0; i < gen; i++) {
      Node cur = ret[i];
      for (int j = 0; j < cur.p; j++) {
        ret[cur.child[j].id] = cur.child[j];
      }
    }
    return ret;
  }

  public String toString() {
    return root.toString("");
  }
}

