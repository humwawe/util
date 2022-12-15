package template.discretization;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hum
 */
public class Discretization {
  int N = 105;
  // a 原始数组
  int[] a = new int[N];
  int len = 0;

  // 原地离散化，len表示离散化后的长度
  void discrete() {
    Arrays.sort(a);
    for (int i = 0; i < a.length; i++) {
      if (i == 0 || a[i] != a[i - 1]) {
        a[len++] = a[i];
      }
    }
  }

  // a=uniq(a)
  int[] uniq(int[] a) {
    //  Arrays.sort(a);
    int p = 0;
    for (int i = 0; i < a.length; i++) {
      if (i == 0 || a[i] != a[i - 1]) {
        a[p++] = a[i];
      }
    }
    return Arrays.copyOf(a, p);
  }

  public int find(int x) {
    // 返回x的位置
    // 负数表示没找到，插入位置需要取反
    return Arrays.binarySearch(a, 0, len, x);
  }

  public int find2(int x) {
    // r取到idx，若x不存在则返回末尾加1的位置
    int l = 0, r = len;
    while (l < r) {
      int mid = l + r >> 1;
      if (a[mid] >= x) {
        r = mid;
      } else {
        l = mid + 1;
      }
    }
    // 映射到0, 1, 2, ...idx
    return l;
  }

  // 使用map映射
  Map<Integer, Integer> map = new HashMap<>();
  int cnt = 0;

  int mapping(int x) {
    if (!map.containsKey(x)) {
      map.put(x, cnt++);
    }
    return map.get(x);
  }

  // 返回反向的映射
  // 对x直接离散化，用0到map.size()-1 的值表示，不影响x的顺序
  // x: [1,10,10,4] -> [0,2,2,1]  map: {1=0,4=1,10=2} ret:[1,4,10]

  public int[] mapping(int[] x) {
    int n = x.length;
    Map<Integer, Integer> map = new HashMap<>(n);
    int cnt = 0;
    int[] a = Arrays.copyOf(x, n);
    int[] b = new int[n];
    Arrays.sort(a);
    for (int value : a) {
      if (!map.containsKey(value)) {
        b[cnt] = value;
        map.put(value, cnt++);
      }
    }
    for (int i = 0; i < n; i++) {
      x[i] = map.get(x[i]);
    }
    return Arrays.copyOf(b, cnt);
  }

}
