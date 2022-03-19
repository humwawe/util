package template.tree;

import java.util.Random;

/**
 * 插入数值
 * 删除数值
 * 查询数值x的排名(若有多个相同的数，返回最小的排名)
 * 查询排名为x的数值
 * 求数值x的前驱(前驱定义为小于x的最大的数)
 * 求数值x的后继(后继定义为大于x的最小的数)
 *
 * @author hum
 */
public class Treap {
  int N = 8;
  int inf = 0x3f3f3f3f;
  Node[] tr = new Node[N];
  int idx;
  int root;

  void init() {
    idx = 0;
    tr[0] = new Node();
    createNode(-inf);
    createNode(inf);
    // 根节点1号点, 右儿子2号点
    root = 1;
    tr[1].r = 2;
    tr[1].parent = 0;
    tr[2].parent = 1;
    pushUp(root);
    if (tr[1].val < tr[2].val) {
      zag(root);
    }
  }

  int createNode(int key) {
    tr[++idx] = new Node();
    tr[idx].key = key;
    tr[idx].val = new Random().nextInt(inf);
    tr[idx].cnt = 1;
    tr[idx].size = 1;
    return idx;
  }

  // insert(root, key, 0)
  void insert(int p, int key, int pre) {
    if (p == 0) {
      int cur = createNode(key);
      tr[cur].parent = pre;
      if (tr[pre].key > key) {
        tr[pre].l = cur;
      } else {
        tr[pre].r = cur;
      }
      return;
    }
    if (tr[p].key == key) {
      tr[p].cnt++;
      pushUp(p);
      return;
    }
    if (tr[p].key > key) {
      insert(tr[p].l, key, p);
      if (tr[tr[p].l].val > tr[p].val) {
        zig(p);
      }
    } else {
      insert(tr[p].r, key, p);
      if (tr[tr[p].r].val > tr[p].val) {
        zag(p);
      }
    }
    pushUp(p);
  }

  // remove(root, key, 0)
  void remove(int p, int key, int pre) {
    if (p == 0) {
      return;
    }
    if (tr[p].key == key) {
      if (tr[p].cnt > 1) {
        tr[p].cnt--;
        pushUp(p);
        return;
      }
      // 如果不是叶子节点
      if (tr[p].l != 0 || tr[p].r != 0) {
        if (tr[p].r == 0 || tr[tr[p].l].val > tr[tr[p].r].val) {
          zig(p);
        } else {
          zag(p);
        }
        remove(p, key, tr[p].parent);
      } else {
        // 叶子节点 直接删除
        if (tr[pre].l == p) {
          tr[pre].l = 0;
        } else {
          tr[pre].r = 0;
        }
      }
      pushUp(pre);
      return;
    }
    if (tr[p].key > key) {
      remove(tr[p].l, key, p);
    } else {
      remove(tr[p].r, key, p);
    }
    pushUp(p);
  }

  // 返回rank，如果存在多个，则返回第一个位置
  // 使用时需要-1，因为哨兵占一个位置
  int getRankByKey(int p, int key) {
    if (p == 0) {
      // 保证没有该元素，也可以返回插入位置
      return 1;
    }
    if (tr[p].key == key) {
      return tr[tr[p].l].size + 1;
    }
    if (tr[p].key > key) {
      return getRankByKey(tr[p].l, key);
    }
    return tr[tr[p].l].size + tr[p].cnt + getRankByKey(tr[p].r, key);
  }

  // rank 从2开始，表示第一
  int getKeyByRank(int p, int rank) {
    if (p == 0) {
      return inf;
    }
    if (tr[tr[p].l].size >= rank) {
      return getKeyByRank(tr[p].l, rank);
    }
    if (tr[tr[p].l].size + tr[p].cnt >= rank) {
      return tr[p].key;
    }
    return getKeyByRank(tr[p].r, rank - tr[tr[p].l].size - tr[p].cnt);
  }

  int getPrev(int p, int key) {
    if (p == 0) {
      return -inf;
    }
    if (tr[p].key >= key) {
      return getPrev(tr[p].l, key);
    }
    return Math.max(tr[p].key, getPrev(tr[p].r, key));
  }

  int getNext(int p, int key) {
    if (p == 0) {
      return inf;
    }
    if (tr[p].key <= key) {
      return getNext(tr[p].r, key);
    }
    return Math.min(tr[p].key, getNext(tr[p].l, key));
  }


  // 右旋
  private void zig(int p) {
    if (p == root) {
      root = tr[p].l;
    }
    int q = tr[p].l;
    tr[p].l = tr[q].r;
    tr[tr[q].r].parent = p;
    tr[q].parent = tr[p].parent;
    if (tr[tr[p].parent].l == p) {
      tr[tr[p].parent].l = q;
    } else {
      tr[tr[p].parent].r = q;
    }
    tr[p].parent = q;
    tr[q].r = p;
    pushUp(p);
    pushUp(q);

  }

  // 左旋
  private void zag(int p) {
    if (p == root) {
      root = tr[p].r;
    }
    int q = tr[p].r;
    tr[p].r = tr[q].l;
    tr[tr[q].l].parent = p;
    tr[q].parent = tr[p].parent;
    if (tr[tr[p].parent].l == p) {
      tr[tr[p].parent].l = q;
    } else {
      tr[tr[p].parent].r = q;
    }
    tr[p].parent = q;
    tr[q].l = p;
    pushUp(p);
    pushUp(q);
  }


  void pushUp(int p) {
    if (p == 0) {
      return;
    }
    tr[p].size = tr[tr[p].l].size + tr[tr[p].r].size + tr[p].cnt;
  }

  class Node {
    int l;
    int r;
    // 节点键值
    int key;
    // 堆中权值
    int val;
    // 这个数出现次数
    int cnt;
    // 每个(节点)子树个数
    int size;
    // 父节点
    int parent;

    public Node() {
      l = 0;
      r = 0;
      size = 0;
      cnt = 0;
    }
  }


}


