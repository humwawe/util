package template.discretization;

/**
 * @author hum
 */
public class Discretization {

    public int find(int x) {
        // arr 存所有待离散化的值
        int[] arr = new int[]{};
        int l = 0, r = arr.length - 1;
        while (l < r) {
            int mid = l + r >> 1;
            if (arr[mid] >= x) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        // 映射到1, 2, ...n
        return r + 1;
    }

}
