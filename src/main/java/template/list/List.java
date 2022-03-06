package template.list;

/**
 * @author hum
 */
public class List {
  // head存储链表头，e[]存储节点的值，ne[]存储节点的next指针，idx表示当前用到了哪个节点
  int n = 100010;
  int head;
  int[] e = new int[n];
  int[] ne = new int[n];
  int idx;

  public List() {
    head = -1;
    idx = 0;
  }

  // 在链表头插入一个数a
  void insert(int a) {
    e[idx] = a;
    ne[idx] = head;
    head = idx;
    idx++;
  }

  // 在第k个插入数后插入一个数a
  void insert(int k, int a) {
    e[idx] = a;
    ne[idx] = ne[k];
    ne[k] = idx;
    idx++;
  }

  // 将头结点删除，需要保证头结点存在
  void remove() {
    head = ne[head];
  }

  // 将第k个插入数后面的点删除
  void remove(int k) {
    ne[k] = ne[ne[k]];
  }

  //    // 双链表
  //    // e[]表示节点的值，l[]表示节点的左指针，r[]表示节点的右指针，idx表示当前用到了哪个节点
  //    int N = 10;
  //    int[] e = new int[N];
  //    int[] l = new int[N];
  //    int[] r = new int[N];
  //    int idx;
  //
  //    // 初始化
  //    void init() {
  //        //0是左端点，1是右端点
  //        r[0] = 1;
  //        l[1] = 0;
  //        idx = 2;
  //    }
  //
  //    // 在节点a的右边插入一个数x
  //    void insert(int a, int x) {
  //        e[idx] = x;
  //        l[idx] = a;
  //        r[idx] = r[a];
  //        l[r[a]] = idx;
  //        r[a] = idx++;
  //    }
  //
  //    // 删除节点a
  //    void remove(int a) {
  //        l[r[a]] = l[a];
  //        r[l[a]] = r[a];
  //    }
}

