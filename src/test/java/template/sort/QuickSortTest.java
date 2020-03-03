package template.sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void quickSort() {
        QuickSort quickSort = new QuickSort();
        int[] q = new int[]{1, 1, 1};
        int l = 0;
        int r = q.length - 1;
        quickSort.quickSort(q, l, r);
    }
}