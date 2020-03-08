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
}

