package template.math;


import java.util.*;

/**
 * @author hum
 */
public class Divisor {
  List<Integer> getDivisors(int x) {
    List<Integer> res = new ArrayList<>();
    for (int i = 1; i * i <= x; i++) {
      if (x % i == 0) {
        res.add(i);
        if (i != x / i) {
          res.add(x / i);
        }
      }
    }
    Collections.sort(res);
    return res;
  }


  // 如果 N = p1^c1 * p2^c2 * ... *pk^ck

  // 约数个数： (c1 + 1) * (c2 + 1) * ... * (ck + 1)
  // int范围类约数最多大概1500左右
  int divideCount(int x) {
    Map<Integer, Integer> map = divide(x);
    int res = 1;
    for (Integer integer : map.values()) {
      res = res * (integer + 1);
    }
    return res;
  }

  // 约数之和： (p1^0 + p1^1 + ... + p1^c1) * ... * (pk^0 + pk^1 + ... + pk^ck)
  long divideSum(int x) {
    Map<Integer, Integer> map = divide(x);
    long res = 1;
    int mod = (int) 1e9 + 7;
    for (Map.Entry<Integer, Integer> kv : map.entrySet()) {
      int p = kv.getKey();
      int c = kv.getValue();
      long t = 1;
      while (c-- > 0) {
        t = (t * p + 1) % mod;
      }
      res = res * t % mod;
    }
    return res;
  }

  Map<Integer, Integer> divide(int x) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 2; i * i <= x; i++) {
      while (x % i == 0) {
        x /= i;
        map.put(i, map.getOrDefault(i, 0) + 1);
      }
    }
    if (x > 1) {
      map.put(x, map.getOrDefault(x, 0) + 1);
    }
    return map;
  }


  // p 保存质因子，t保存次数
  int[][] divide2(int x) {
    int len = 0;
    int N = 65;
    int[] p = new int[N];
    int[] t = new int[N];
    for (int i = 2; i * i <= x; i++) {
      if (x % i == 0) {
        p[len] = i;
        t[len] = 0;
        while (x % i == 0) {
          t[len]++;
          x /= i;
        }
        len++;
      }
    }
    if (x > 1) {
      p[len] = x;
      t[len] = 1;
      len++;
    }
    return new int[][]{Arrays.copyOf(p, len), Arrays.copyOf(t, len)};
  }


  // 如果需要对很多数分解，可以考虑线性筛预处理出每个数的最小质因子，随后直接相除，不需要试余数判断
  // 存每个数的最小质因子
  // int[] lpf = enumLowestPrimeFactors(N);
  // factorFast(x, lpf)
  // 返回素数列表和次数分别存在 ret[i][0] ret[i][1]
  public int[][] factorFast(int n, int[] lpf) {
    int[][] f = new int[9][];
    int q = 0;
    while (lpf[n] > 0) {
      int p = lpf[n];
      if (q == 0 || p != f[q - 1][0]) {
        f[q++] = new int[]{p, 1};
      } else {
        f[q - 1][1]++;
      }
      n /= p;
    }
    return Arrays.copyOf(f, q);
  }

  // lpf[i]每个数的最小质因子
  public int[] enumLowestPrimeFactors(int n) {
    int tot = 0;
    int[] lpf = new int[n + 1];
    int u = n + 32;
    double lu = Math.log(u);
    int[] primes = new int[(int) (u / lu + u / lu / lu * 1.5)];
    for (int i = 2; i <= n; i++) {
      lpf[i] = i;
    }
    for (int p = 2; p <= n; p++) {
      if (lpf[p] == p) {
        primes[tot++] = p;
      }
      int tmp;
      for (int i = 0; i < tot && primes[i] <= lpf[p] && (tmp = primes[i] * p) <= n; i++) {
        lpf[tmp] = primes[i];
      }
    }
    return lpf;
  }


}
