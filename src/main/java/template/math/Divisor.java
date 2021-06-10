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
}
