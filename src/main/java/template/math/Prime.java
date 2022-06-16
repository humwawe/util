package template.math;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hum
 */
public class Prime {
  boolean isPrime(int x) {
    if (x < 2) {
      return false;
    }
    for (int i = 2; i <= x / i; i++) {
      if (x % i == 0) {
        return false;
      }
    }
    return true;
  }

  // 埃氏筛法
  void getPrimes(int n) {
    int[] primes = new int[n + 1];
    boolean[] vis = new boolean[n + 1];
    int cnt = 0;
    for (int i = 2; i <= n; i++) {
      if (vis[i]) {
        continue;
      }
      primes[cnt++] = i;
      for (int j = i + i; j <= n; j += i) {
        vis[j] = true;
      }
    }
  }

  // 埃氏筛法优化，从x^2开始，将x^2,(x+1)*x,..n/x*x标记
  void getPrimesQuick(int n) {
    int[] primes = new int[n + 1];
    boolean[] vis = new boolean[n + 1];
    int cnt = 0;
    for (int i = 2; i <= n; i++) {
      if (vis[i]) {
        continue;
      }
      primes[cnt++] = i;
      for (int j = i; j <= n / i; j++) {
        vis[i * j] = true;
      }
    }
  }

  //线性筛法，每个合数被最小的质因子筛掉
  void getPrimes2(int n) {
    int[] primes = new int[n + 1];
    boolean[] vis = new boolean[n + 1];
    int cnt = 0;
    for (int i = 2; i <= n; i++) {
      if (!vis[i]) {
        primes[cnt++] = i;
      }
      for (int j = 0; primes[j] <= n / i; j++) {
        vis[primes[j] * i] = true;
        if (i % primes[j] == 0) {
          break;
        }
      }
    }
  }


  //线性筛法，每个合数被最小的质因子筛掉
  // sieve[i] == i 时 i 即为质数
  void getPrimes3(int n) {
    int[] primes = new int[n + 1];
    // sieve存每个数的最小质因子
    int[] sieve = new int[n + 1];
    int cnt = 0;
    for (int i = 2; i <= n; i++) {
      if (sieve[i] == 0) {
        sieve[i] = i;
        primes[cnt++] = i;
      }
      // 给当前i乘一个质因子
      for (int j = 0; j < cnt; j++) {
        // 超出范围，或者当前质因数已经不是最小质因子
        if (primes[j] > n / i || primes[j] > sieve[i]) {
          break;
        }
        sieve[i * primes[j]] = primes[j];
      }
    }
  }

  // 求n以内的素数，空间和时间最优
  int[] sieveEratosthenes(int n) {
    if (n <= 32) {
      int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
      for (int i = 0; i < primes.length; i++) {
        if (n < primes[i]) {
          return Arrays.copyOf(primes, i);
        }
      }
      return primes;
    }

    int u = n + 32;
    double lu = Math.log(u);
    int[] ret = new int[(int) (u / lu + u / lu / lu * 1.5)];
    ret[0] = 2;
    int pos = 1;

    int[] isnp = new int[(n + 1) / 32 / 2 + 1];
    int sup = (n + 1) / 32 / 2 + 1;

    int[] tprimes = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
    for (int tp : tprimes) {
      ret[pos++] = tp;
      int[] ptn = new int[tp];
      for (int i = (tp - 3) / 2; i < tp << 5; i += tp) {
        ptn[i >> 5] |= 1 << (i & 31);
      }
      for (int j = 0; j < sup; j += tp) {
        for (int i = 0; i < tp && i + j < sup; i++) {
          isnp[j + i] |= ptn[i];
        }
      }
    }

    // 3,5,7
    // 2x+3=n
    int[] magic = {0, 1, 23, 2, 29, 24, 19, 3, 30, 27, 25, 11, 20, 8, 4, 13, 31, 22, 28, 18, 26, 10, 7, 12, 21, 17, 9, 6, 16, 5, 15, 14};
    int h = n / 2;
    for (int i = 0; i < sup; i++) {
      for (int j = ~isnp[i]; j != 0; j &= j - 1) {
        int pp = i << 5 | magic[(j & -j) * 0x076be629 >>> 27];
        int p = 2 * pp + 3;
        if (p > n) {
          break;
        }
        ret[pos++] = p;
        if ((long) p * p > n) {
          continue;
        }
        for (int q = (p * p - 3) / 2; q <= h; q += p) {
          isnp[q >> 5] |= 1 << q;
        }
      }
    }

    return Arrays.copyOf(ret, pos);
  }

  List<Integer> prime = new ArrayList<>();
  int N = 105;
  int[] sieve = new int[N + 1];

  // sieve存每个数的最小质因子，也是线性筛
  void sieve() {
    sieve[1] = 1;
    for (int i = 2; i <= N; i++) {
      if (sieve[i] == 0) {
        sieve[i] = i;
        prime.add(i);
      }
      for (int j = 0; j < prime.size() && prime.get(j) <= sieve[i] && i * prime.get(j) <= N; j++) {
        sieve[i * prime.get(j)] = prime.get(j);
      }
    }
  }

  // 素数分解
  void divide(int x) {
    for (int i = 2; i <= x / i; i++) {
      if (x % i == 0) {
        // 个数
        int s = 0;
        while (x % i == 0) {
          x /= i;
          s++;
        }
        System.out.println(i + ":" + s);
      }
    }
    if (x > 1) {
      System.out.println(x + ":" + 1);
    }
  }


}
