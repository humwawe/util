package template.tree;

/**
 * @author hum
 */
public class BinaryIndexedTree {
    int N = 105;
    int[] t = new int[N];
    int n;

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
}
