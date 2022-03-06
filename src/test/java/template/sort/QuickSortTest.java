package template.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class QuickSortTest {

  @Test
  void quickSort() {
    QuickSort quickSort = new QuickSort();
    int[] q = new int[]{1, 2, 1, 3, -1, -12, -9};
    int l = 0;
    int r = q.length - 1;
    quickSort.quickSort(q, l, r);
    System.out.println(Arrays.toString(q));
  }

  @Test
  void radioSort() {
    QuickSort quickSort = new QuickSort();
    int[] q = new int[]{1, 2, 1, 3, -1, -12, -9, 10};
    quickSort.intRadixSort(q);
    System.out.println(Arrays.toString(q));
    long[] q2 = new long[]{1, 2, 1, 3, -1, -13414, -9, -123423, -2345234, -23452, -2345245, 10000000000000L};
    quickSort.longRadixSort(q2);
    System.out.println(Arrays.toString(q2));
  }
}