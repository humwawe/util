package template.tree;

/**
 * @author hum
 * 动态加点
 */
public class SegmentTree4 {
  Node root;

  void pushUp(Node u) {
    u.sum = u.leftChild.sum + u.rightChild.sum;
  }

  void pushDown(Node u) {
    if (u.add != 0) {
      int mid = u.l + u.r >> 1;
      if (u.leftChild == null) {
        u.leftChild = new Node(u.l, mid);
      }
      if (u.rightChild == null) {
        u.rightChild = new Node(mid + 1, u.r);
      }
      u.leftChild.add += u.add;
      u.leftChild.sum += (u.leftChild.r - u.leftChild.l + 1) * u.add;
      u.rightChild.add += u.add;
      u.rightChild.sum += (u.rightChild.r - u.rightChild.l + 1) * u.add;
      u.add = 0;
    }
  }

  void build(int start, int end) {
    root = new Node(start, end);
  }

  void modify(Node u, int l, int r, int d) {
    if (u.l >= l && u.r <= r) {
      u.sum += (u.r - u.l + 1) * d;
      u.add += d;
      return;
    }
    int mid = u.l + u.r >> 1;
    if (u.leftChild == null) {
      u.leftChild = new Node(u.l, mid);
    }
    if (u.rightChild == null) {
      u.rightChild = new Node(mid + 1, u.r);
    }
    // 分裂
    pushDown(u);

    if (r <= mid) {
      modify(u.leftChild, l, r, d);
    } else if (l > mid) {
      modify(u.rightChild, l, r, d);
    } else {
      modify(u.leftChild, l, r, d);
      modify(u.rightChild, l, r, d);
    }

    pushUp(u);
  }

  // 从u开始查找
  int query(Node u, int l, int r) {
    if (u == null) {
      return 0;
    }
    if (u.l >= l && u.r <= r) {
      return u.sum;
    }

    pushDown(u);
    int mid = u.l + u.r >> 1;
    if (r <= mid) {
      return query(u.leftChild, l, r);
    } else if (l > mid) {
      return query(u.rightChild, l, r);
    } else {
      return query(u.leftChild, l, r) + query(u.rightChild, l, r);
    }

  }

  // 合并两个线段树到p上，将对应值相加
  // s1.root = s1.merge(s1.root, s2.root);
  Node merge(Node p, Node q) {
    if (p == null) {
      return q;
    }
    if (q == null) {
      return p;
    }

    pushDown(p);
    pushDown(q);

    // 合并到最后
    if (p.l == p.r) {
      p.sum += q.sum;
      //  p.add += q.add;
      return p;
    }
    p.leftChild = merge(p.leftChild, q.leftChild);
    p.rightChild = merge(p.rightChild, q.rightChild);

    pushUp(p);

    return p;
  }

  class Node {
    int l;
    int r;
    Node leftChild;
    Node rightChild;
    int sum;
    // 懒标记，给儿子加上add，不包括自己
    int add;

    public Node(int l, int r) {
      this.l = l;
      this.r = r;
    }

    public Node(int l, int r, int sum, int add) {
      this.l = l;
      this.r = r;
      this.sum = sum;
      this.add = add;
    }
  }
}
