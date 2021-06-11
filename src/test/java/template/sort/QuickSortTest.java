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
        int l = 0;
        int r = q.length - 1;
        quickSort.radixSort2(q);
        System.out.println(Arrays.toString(q));
    }
}