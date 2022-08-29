package template.tree;

/**
 * @author hum
 */
public class BinaryIndexedTree {
  int N = 105;
  int[] t = new int[N];

  // n&(~n+1) ~n=-1-n
  // Integer.lowestOneBit(x)
  int lowbit(int x) {
    return x & -x;
  }

  // 位置从1开始，不能有0，否则lowbit会死循环
  // 对某个位置进行加c操作，如果是修改可以转变为加上：修改值与原数的差值
  void add(int x, int c) {
    for (int i = x; i < N; i += lowbit(i)) {
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
    int[] sum = new int[N];
    for (int i = 1; i < N; i++) {
      sum[i] = sum[i - 1] + a[i];
      t[i] = sum[i] - sum[i - lowbit(i)];
    }
  }

  // 树状数组上二分
  // 查询最大的位置满足 前缀和 <= s
  int query(int s) {
    int pos = 0;
    // i从最高位开始，log(n)的上界
    for (int i = 18; i >= 0; i--) {
      if (pos + (1 << i) < N && t[pos + (1 << i)] <= s) {
        pos += 1 << i;
        s -= t[pos];
      }
    }
    return pos;
  }

  // 区间修改，区间查询，构建一个d1差分数组，在d1的基础构建d2 = d1[i]*i
  int[] d1 = new int[N];
  int[] d2 = new int[N];

  // 对第一个数组数组add(c)
  // 第二个维护x*c的树状数组
  void add2(int x, int c) {
    for (int i = x; i < N; i += lowbit(i)) {
      d2[i] += x * c;
    }
  }

  int sum2(int x) {
    int res = 0;
    for (int i = x; i > 0; i -= lowbit(i)) {
      res += d2[i];
    }
    return res;
  }

  // sum为第一个树状数组的值 sum2为第二个数组数组的值
  int ask(int x) {
    return (x + 1) * sum(x) - sum2(x);
  }

  // 二维操作
  int[][] maz;

  // 二维区间修改，单点查询，构建差分数组，初始化的时候使用a[i][j]-(a[i-1][j]+a[i][j-1]-a[i-1][j-1])的差
  // 修改四个角
  void rangeAdd(int xa, int ya, int xb, int yb, int z) {
    add(xa, ya, z);
    add(xa, yb + 1, -z);
    add(xb + 1, ya, -z);
    add(xb + 1, yb + 1, z);
  }

  void add(int x, int y, int z) {
    for (int i = x; i < N; i += lowbit(i)) {
      for (int j = y; j < N; j += lowbit(j)) {
        maz[i][j] += z;
      }
    }
  }

  int sum(int x, int y) {
    int res = 0;
    for (int i = x; i > 0; i -= lowbit(i)) {
      for (int j = y; j > 0; j -= lowbit(j)) {
        res += maz[i][j];
      }
    }
    return res;
  }
}
