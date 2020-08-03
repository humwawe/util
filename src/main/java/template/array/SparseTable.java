package template.array;

/**
 * @author hum
 */
public class SparseTable {
    int inf = 0x3f3f3f3f;
    int N = (int) (1e5 + 5);
    int logn = 21;
    int[] lg2 = new int[N];
    // dp[i][j] 保留i开始2^j个数中的最大值
    int[][] dp = new int[N][logn];
    int n = 5;

    // 预处理log函数值
    void initPre() {
        lg2[0] = -1;
        for (int i = 1; i < N; i++) {
            lg2[i] = lg2[i >> 1] + 1;
        }
    }

    // a下标 [0,n)
    void init(int[] a) {
        for (int i = 0; i < n; i++) {
            dp[i][0] = a[i];
        }
        int t = lg2[n] / lg2[2] + 1;
        for (int j = 1; j < t; j++) {
            for (int i = 0; i + (1 << j) - 1 < n; i++) {
                dp[i][j] = Math.max(dp[i][j - 1], dp[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    // 查询区间[l,r] 从0开始
    int query(int l, int r) {
        if (l > r) {
            return (int) inf;
        }
        int k = lg2[r - l + 1];
        return Math.max(dp[l][k], dp[r - (1 << k) + 1][k]);
    }
}
