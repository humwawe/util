package template.search;

/**
 * @author hum
 */
public class BinarySearch {

  // 检查x是否满足某种性质
  boolean check(int x) {
    return true;
  }

  // 区间[l, r]被划分成[l, mid]和[mid + 1, r]时使用：
  int bsearch1(int l, int r) {
    while (l < r) {
      int mid = l + r >> 1;
      // check()判断mid是否满足性质
      if (check(mid)) {
        r = mid;
      } else {
        l = mid + 1;
      }
    }
    return l;
  }

  // 区间[l, r]被划分成[l, mid - 1]和[mid, r]时使用：
  int bsearch2(int l, int r) {
    while (l < r) {
      int mid = l + r + 1 >> 1;
      if (check(mid)) {
        l = mid;
      } else {
        r = mid - 1;
      }
    }
    return l;
  }

  // 检查x是否满足某种性质
  boolean check(double x) {
    return true;
  }

  double bsearch3(double l, double r) {
    // eps 表示精度，取决于题目对精度的要求
    // 保留k位小数，一般可以取10^-(k+2)
    // 精度不好确认，可以采用固定循环次数的方法
    final double eps = 1e-6;
    while (r - l > eps) {
      double mid = (l + r) / 2;
      if (check(mid)) {
        r = mid;
      } else {
        l = mid;
      }
    }
    return l;
  }

  // [l,r) 从左往右返回第一个大于等于t的位置，返回插入位置(原数组有相等时靠前)，如果结果到r(a[r]可能越界)
  int lowerBound(int[] a, int t, int l, int r) {
    while (l < r) {
      int mid = l + r >> 1;
      if (a[mid] >= t) {
        r = mid;
      } else {
        l = mid + 1;
      }
    }
    return l;
  }

  // [l,r) 从左往右找第一个大于t的数，返回插入位置(原数组有相等时靠后)，如果结果到r(a[r]可能越界)
  int upperBound(int[] a, int t, int l, int r) {
    while (l < r) {
      int mid = l + r >> 1;
      if (a[mid] <= t) {
        l = mid + 1;
      } else {
        r = mid;
      }
    }
    return l;
  }


  // 查找第一个与 t 相等的位置，负数表明未找到，可以求反即为应该插入的位置
  // lowerBound 且等于 t
  int firstEqIdx(int[] a, int t, int l, int r) {
    int x = lowerBound(a, t, l, r);
    if (x < a.length && a[x] == t) {
      return x;
    }
    // x: [0,n]
    // ~x=-x-1:[-n-1,-1]
    return ~x;
  }

  // 查找最后一个与 t 相等的位置，负数表明未找到
  // upperBound 前一个且等于 t
  int lastEqIdx(int[] a, int t, int l, int r) {
    int x = upperBound(a, t, l, r);
    if (x - 1 >= 0 && a[x - 1] == t) {
      return x - 1;
    }
    return ~x;
  }

  // 查找最后一个等于或小于 t 的元素
  // upperBound 前一个
  int lastEqLeEle(int[] a, int t, int l, int r) {
    int x = upperBound(a, t, l, r);
    if (x - 1 >= 0) {
      return a[x - 1];
    }
    return -0x3f3f3f3f;
  }

  // 查找最后一个小于 t 的元素
  // lowerBound 前一个
  int lastLeEle(int[] a, int t, int l, int r) {
    int x = lowerBound(a, t, l, r);
    if (x - 1 >= 0) {
      return a[x - 1];
    }
    return -0x3f3f3f3f;
  }

  // 查找等于 t 的元素个数
  int eqCnt(int[] a, int t, int l, int r) {
    return upperBound(a, t, l, r) - lowerBound(a, t, l, r);
  }

  // [l,r] 闭区间，从右往左返回第一个小于等于t的位置，最小最多到0
  int upperBound2(int[] a, int t, int l, int r) {
    while (l < r) {
      int mid = l + r + 1 >> 1;
      if (a[mid] <= t) {
        l = mid;
      } else {
        r = mid - 1;
      }
    }
    return l;
  }


}
