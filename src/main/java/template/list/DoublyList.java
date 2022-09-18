package template.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hum
 */
public class DoublyList {
  // 双链表
  // e[]表示节点的值(从2开始)
  int N = 105;
  int[] e = new int[N];
  // l[]表示节点的左指针，r[]表示节点的右指针
  int[] l = new int[N];
  int[] r = new int[N];
  // idx表示当前用到了哪个节点
  int idx;

  // 初始化
  public DoublyList() {
    //0是左端点，1是右端点
    Arrays.fill(e, -1);
    Arrays.fill(l, -1);
    Arrays.fill(r, -1);
    r[0] = 1;
    l[1] = 0;
    idx = 2;
  }

  // 将 a 加入 双向链表
  public DoublyList(int[] a) {
    //0是左端点，1是右端点
    Arrays.fill(e, -1);
    Arrays.fill(l, -1);
    Arrays.fill(r, -1);
    r[0] = 1;
    l[1] = 0;
    idx = 2;
    for (int value : a) {
      insertBeforeTail(value);
    }
  }

  // 在节点a的右边插入一个数x
  // 插入节点按顺序编号，从2开始（0,1为哨兵）
  void insert(int a, int x) {
    e[idx] = x;
    l[idx] = a;
    r[idx] = r[a];
    l[r[a]] = idx;
    r[a] = idx++;
  }

  void insertFromHead(int x) {
    // 加在第0个节点后面
    int a = 0;
    insert(a, x);
  }

  void insertBeforeTail(int x) {
    // 加在第1个节点左边节点的后面
    int a = l[1];
    insert(a, x);
  }

  // 删除节点a 从2开始（只是更改指针指向，e中存的节点数据没变）
  void remove(int a) {
    if (a < 2 || a >= idx) {
      return;
    }
    l[r[a]] = l[a];
    r[l[a]] = r[a];
  }

  @Override
  public String toString() {
    List<Integer> res = new ArrayList<>();
    for (int pos = 0; pos != -1; pos = r[pos]) {
      res.add(e[pos]);
    }
    return res.toString();
  }


}
