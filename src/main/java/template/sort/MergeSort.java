package template.sort;

/**
 * @author hum
 */
public class MergeSort {
  int[] tmp = new int[1000000];

  /**
   * @param q array
   * @param l 0
   * @param r len - 1
   */
  void mergeSort(int q[], int l, int r) {
    if (l >= r) {
      return;
    }

    int mid = l + r >> 1;
    mergeSort(q, l, mid);
    mergeSort(q, mid + 1, r);
    int k = 0, i = l, j = mid + 1;
    while (i <= mid && j <= r) {
      if (q[i] < q[j]) {
        tmp[k++] = q[i++];
      } else {
        tmp[k++] = q[j++];
      }
    }
    while (i <= mid) {
      tmp[k++] = q[i++];
    }
    while (j <= r) {
      tmp[k++] = q[j++];
    }
    for (i = l, j = 0; i <= r; i++, j++) {
      q[i] = tmp[j];
    }
  }

}
