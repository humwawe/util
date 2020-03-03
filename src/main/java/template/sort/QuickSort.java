package template.sort;

/**
 * @author hum
 */
public class QuickSort {
    /**
     * @param q array
     * @param l 0
     * @param r len - 1
     */
    void quickSort(int q[], int l, int r) {
        if (l >= r) {
            return;
        }
        // x可以取q[l]，但是不能取q[r]
        int i = l - 1, j = r + 1, x = q[l + r >> 1];
        while (i < j) {
            do {
                i++;
            } while (q[i] < x);
            do {
                j--;
            } while (q[j] > x);
            if (i < j) {
                int tmp = q[i];
                q[i] = q[j];
                q[j] = tmp;
            }
        }
        quickSort(q, l, j);
        quickSort(q, j + 1, r);
    }

}
