package template.tree;

/**
 * @author hum
 */
public class BinaryIndexedTree {
    int N = 105;
    int[] t = new int[N];
    int n;

    // n&(~n+1) ~n=-1-n
    // Integer.lowestOneBit(x)
    int lowbit(int x) {
        return x & -x;
    }

    // 位置从1开始
    void add(int x, int c) {
        for (int i = x; i <= n; i += lowbit(i)) {
            t[i] += c;
        }
    }

    // 可以直接查询前缀和
    // 区间查询[l,r]为: sum[r]-sum[l-1]
    // 区间修改，单点查询，可以构建差分数组，初始化的时候使用a[i]-a[i-1]，修改使用add(l,d);add(r+1,-d)，单个点x的值为sum[x]
    // 区间修改，区间查询，可以构建两个差分数组，一个正常的，一个使用i*(a[i]-a[i-1])的，比较麻烦可以考虑线段树
    int sum(int x) {
        int res = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            res += t[i];
        }
        return res;
    }

    // 对每个元素使用add方法初始化，复杂度为O(Nlog(N))
    // 线性初始化，记录前缀和构造
    void init(int[] a) {
        int[] sum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + a[i];
            t[i] = sum[i] - sum[i - lowbit(i)];
        }
    }

    // 区间修改，区间查询，在t的基础构建t2
    int[] t2 = new int[N];

    // 对第一个数组数组add(c)
    // 第二个维护x*c的树状数组
    void add2(int x, int c) {
        for (int i = x; i <= n; i += lowbit(i)) {
            t2[i] += x * c;
        }
    }

    int sum2(int x) {
        int res = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            res += t2[i];
        }
        return res;
    }

    // sum为第一个树状数组的值 sum2为第二个数组数组的值
    int ask(int x) {
        return (x + 1) * sum(x) - sum2(x);
    }
}
