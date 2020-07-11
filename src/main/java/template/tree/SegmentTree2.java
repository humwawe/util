package template.tree;

/**
 * @author hum
 */
public class SegmentTree2 {
    int N = 105;
    Node[] tr = new Node[N * 4];
    int n = 55;
    int[] w = new int[n];

    void pushUp(int u) {
        tr[u].sum = tr[u << 1].sum + tr[u << 1 | 1].sum;
    }

    void pushDown(int u) {
        if (tr[u].add != 0) {
            tr[u << 1].add += tr[u].add;
            tr[u << 1].sum += (tr[u << 1].r - tr[u << 1].l + 1) * tr[u].add;
            tr[u << 1 | 1].add += tr[u].add;
            tr[u << 1 | 1].sum += (tr[u << 1 | 1].r - tr[u << 1 | 1].l + 1) * tr[u].add;
            tr[u].add = 0;
        }
    }

    // 从u开始，构建[l,r]的树，w[i]存每个节点的值(1开始)
    void build(int u, int l, int r) {
        if (l == r) {
            tr[u] = new Node(l, r, w[r], 0);
            return;
        }
        tr[u] = new Node(l, r);
        int mid = l + r >> 1;
        build(u << 1, l, mid);
        build(u << 1 | 1, mid + 1, r);
        pushUp(u);
    }

    // 从u开始查找
    int query(int u, int l, int r) {
        // 已经完全在[l,r]中了
        if (tr[u].l >= l && tr[u].r <= r) {
            return tr[u].sum;
        }
        pushDown(u);
        int mid = tr[u].l + tr[u].r >> 1;
        int sum = 0;
        if (l <= mid) {
            sum += query(u << 1, l, r);
        }
        if (r > mid) {
            sum += query(u << 1 | 1, l, r);
        }
        return sum;
    }

    void modify(int u, int l, int r, int d) {
        if (tr[u].l >= l && tr[u].r <= r) {
            tr[u].sum += (tr[u].r - tr[u].l + 1) * d;
            tr[u].add += d;
        } else { // 分裂
            pushDown(u);
            int mid = tr[u].l + tr[u].r >> 1;
            if (l <= mid) {
                modify(u << 1, l, r, d);
            }
            if (r > mid) {
                modify(u << 1 | 1, l, r, d);
            }
            pushUp(u);
        }
    }

    class Node {
        int l;
        int r;
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
