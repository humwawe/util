package template.tree;

/**
 * @author hum
 * 动态加点
 */
public class SegmentTree3 {
  Node root;

  void pushUp(Node u) {
    u.sum = u.leftChild.sum + u.rightChild.sum;
  }

  void pushDown(Node u) {
    if (u.add != 0) {
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
    if (l <= mid) {
      modify(u.leftChild, l, r, d);
    }
    if (r > mid) {
      modify(u.rightChild, l, r, d);
    }
    pushUp(u);
  }

  // 从u开始查找
  int query(Node u, int l, int r) {
    // 已经完全在[l,r]中了
    if (u.l >= l && u.r <= r) {
      return u.sum;
    }
    int mid = u.l + u.r >> 1;
    if (u.leftChild == null) {
      u.leftChild = new Node(u.l, mid);
    }
    if (u.rightChild == null) {
      u.rightChild = new Node(mid + 1, u.r);
    }
    pushDown(u);
    int sum = 0;
    if (l <= mid) {
      sum += query(u.leftChild, l, r);
    }
    if (r > mid) {
      sum += query(u.rightChild, l, r);
    }
    return sum;
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
