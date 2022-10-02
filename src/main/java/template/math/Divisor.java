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


  int N = (int) (1e5 + 5);
  // 如果需要对很多数分解，可以考虑线性筛预处理出每个数的最小质因子，随后直接相除，不需要试余数判断
  // 存每个数的最小质因子
  int[] p = new int[N];

  void initP() {
    for (int i = 0; i < N; i++) {
      p[i] = i;
    }
    for (int i = 2; i < N; i++) {
      if (p[i] == i) {
        for (int j = i + i; j < N; j += i) {
          if (p[j] == j) {
            p[j] = i;
          }
        }
      }
    }
  }

  Map<Integer, Integer> divideWithP(int x) {
    Map<Integer, Integer> map = new HashMap<>();
    while (x > 1) {
      int y = p[x];
      int z = 0;
      for (; x % y == 0; x /= y) {
        ++z;
      }
      map.put(y, z);
    }
    return map;
  }
}
