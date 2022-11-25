package template.other;

import java.util.Arrays;

/**
 * @author hum
 */
public class Mo {
  // 分块大小，一般为 sqrt(n)
  int B = 500;
  int m;
  int tmpAns;
  int[] left;
  int[] right;
  int[] id;

  public Mo(int m) {
    this.m = m;
    left = new int[m];
    right = new int[m];
    id = new int[m];
  }

  public Mo(int m, int[] left, int[] right, int[] id) {
    this.m = m;
    this.left = left;
    this.right = right;
    this.id = id;
  }

  int[] mo() {
    Integer[] idx = new Integer[m];
    for (int i = 0; i < m; i++) {
      idx[i] = i;
    }
    // 按块号（左端点决定）排序
    // 对奇偶的块按右大或小排序
    Arrays.sort(idx, (x, y) -> {
      int d = left[x] / B - left[y] / B;
      if (d == 0) {
        if (left[x] / B % 2 == 0) {
          return right[x] - right[y];
        }
        return right[y] - right[x];
      }
      return d;
    });


    int[] res = new int[m];
    int l = 0;
    int r = -1;
    // 对所有询问
    for (int i = 0; i < m; i++) {
      // 扩展
      int j = idx[i];
      while (r < right[j]) {
        add(++r);
      }
      while (l > left[j]) {
        add(--l);
      }
      // 缩小
      while (r > right[j]) {
        del(r--);
      }
      while (l < left[j]) {
        del(l++);
      }
      res[id[j]] = tmpAns;
    }

    return res;

  }

  private void add(int i) {
    // update tmpAns
  }

  private void del(int i) {
    // update tmpAns
  }


}
