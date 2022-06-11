package template.math;


import java.util.ArrayList;
import java.util.List;

/**
 * @author hum
 */
public class Combination {
  int N = 5005;
  long[][] c = new long[N][N];
  int mod = (int) (1e9 + 7);

  // 递归法求组合数 N^2
  void combination() {
    // c[a][b] 表示从a个里中选b个的方案数
    for (int i = 0; i < N; i++) {
      for (int j = 0; j <= i; j++) {
        if (j == 0) {
          c[i][j] = 1;
        } else {
          c[i][j] = (c[i - 1][j] + c[i - 1][j - 1]) % mod;
        }
      }
    }
  }

  int qmi(int m, int k, int p) {
    long res = 1 % p, t = m;
    while (k > 0) {
      if ((k & 1) == 1) {
        res = res * t % p;
      }
      t = t * t % p;
      k >>= 1;
    }
    return (int) res % p;
  }


  long[] fact = new long[N];
  long[] infact = new long[N];

  // 通过预处理逆元的方式求组合数 N*logN（当mod为质数，根据欧拉定理i^(p-2)即为i的逆元）
  // 从a个里中选b个的方案数：fact[a]*infact[b]%mod*infact[a-b]%mod
  // infact[a] = qmi(fact[a], mod - 2, mod)，有时候可以不算infact，需要用时采用fact计算
  void fact() {
    // 预处理阶乘的余数和阶乘逆元的余数
    fact[0] = infact[0] = 1;
    for (int i = 1; i < N; i++) {
      fact[i] = fact[i - 1] * i % mod;
      infact[i] = infact[i - 1] * qmi(i, mod - 2, mod) % mod;
    }
  }

  //预处理 fact 和 infact 后使用求组合数
  long c(int a, int b) {
    return fact[a] * infact[b] % mod * infact[a - b] % mod;
  }

  // 返回的ret[0] 为fact数组（f）
  // 返回的ret[1] 为infact数组（invf）
  long[][] enumFAndIf(int n, int mod) {
    long[] f = new long[n + 1];
    long[] invf = new long[n + 1];
    f[0] = 1;
    for (int i = 1; i <= n; i++) {
      f[i] = f[i - 1] * i % mod;
    }
    invf[n] = invl(f[n], mod);
    for (int i = n - 1; i >= 0; i--) {
      invf[i] = invf[i + 1] * (i + 1) % mod;
    }
    return new long[][]{f, invf};
  }

  // a在mod下的逆元，mod可不为质数
  long invl(long a, long mod) {
    long b = mod;
    long p = 1, q = 0;
    while (b > 0) {
      long c = a / b;
      long d;
      d = a;
      a = b;
      b = d % b;
      d = p;
      p = q;
      q = d - c * q;
    }
    return p < 0 ? p + mod : p;
  }

  // 预处理 enumFAndIf 后求组合数
  long c(int a, int b, int mod, int[][] fif) {
    if (b < 0 || b > a) {
      return 0;
    }
    return (long) fif[0][a] * fif[1][b] % mod * fif[1][a - b] % mod;
  }

  // Lucas定理, 若p是质数
  // c[a][b] % p = c[a % p][b % p] * c[a / p][b / p] % p
  int p = 107;

  long lucas(long a, long b) {
    if (a < p && b < p) {
      return comb((int) a, (int) b) % p;
    }
    return comb((int) a % p, (int) b % p) * lucas(a / p, b / p) % p;
  }

  // 通过定理求组合数Comb(a, b)
  long comb(int a, int b) {
    long res = 1;
    for (int i = 1, j = a; i <= b; i++, j--) {
      res = res * j % p;
      res = res * qmi(i, p - 2, p) % p;
    }
    return res % p;
  }


  // 高精度组合数，可以支持如c[5000][2500]
  int[] primes = new int[N];
  int cnt;     // 存储所有质数
  int[] sum = new int[N];     // 存储每个质数的次数
  boolean[] st = new boolean[N];     // 存储每个数是否已被筛掉

  List<Integer> comb2(int a, int b) {
    // 预处理范围内的所有质数
    getPrimes(a);
    // 求每个质因数的次数
    for (int i = 0; i < cnt; i++) {
      int p = primes[i];
      sum[i] = getNp(a, p) - getNp(b, p) - getNp(a - b, p);
    }
    List<Integer> res = new ArrayList<>();
    res.add(1);
    //        BigInteger bigInteger = new BigInteger("1");
    // 用高精度乘法将所有质因子相乘
    for (int i = 0; i < cnt; i++) {
      for (int j = 0; j < sum[i]; j++) {
        res = mul(res, primes[i]);
        //                bigInteger = bigInteger.multiply(new BigInteger(String.valueOf(primes[i])));
      }
    }
    return res;
  }

  // 线性筛法求素数
  void getPrimes(int n) {
    for (int i = 2; i <= n; i++) {
      if (!st[i]) {
        primes[cnt++] = i;
      }
      for (int j = 0; primes[j] <= n / i; j++) {
        st[primes[j] * i] = true;
        if (i % primes[j] == 0) {
          break;
        }
      }
    }
  }

  // 求n！中p的次数
  int getNp(int n, int p) {
    int res = 0;
    while (n > 0) {
      res += n / p;
      n /= p;
    }
    return res;
  }

  // 高精度乘
  List<Integer> mul(List<Integer> a, int b) {
    List<Integer> c = new ArrayList<>();
    int t = 0;
    for (int i = 0; i < a.size(); i++) {
      t += a.get(i) * b;
      c.add(t % 10);
      t /= 10;
    }
    while (t > 0) {
      c.add(t % 10);
      t /= 10;
    }
    return c;
  }

  // 给定n个0和n个1组成序列，满足任意前缀中0的个数都不少于1的个数的序列的数量为卡特兰数
  // Cat(n) = C(2n, n) - C(2n, n - 1) = C(2n, n) / (n + 1)

}
