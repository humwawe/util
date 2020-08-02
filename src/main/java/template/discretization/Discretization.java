package template.discretization;

import java.util.Arrays;

/**
 * @author hum
 */
public class Discretization {
    int N = 105;
    // a 原始数组
    int[] a = new int[N];
    // b 存所有待离散化的值，下标1开始
    int[] b = new int[N + 1];
    int idx = 0;

    void discrete() {
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
        for (int i = 0; i < N; i++) {
            if (i == 0 || a[i] != a[i - 1]) {
                b[++idx] = a[i];
            }
        }
    }

    public int find(int x) {
        // 返回x的位置
        // 负数表示没找到，插入位置需要取反
        return Arrays.binarySearch(b, 1, idx + 1, x);
    }

    public int find2(int x) {
        // r取到idx+1，若x不存在则返回末尾加1的位置
        int l = 1, r = idx + 1;
        while (l < r) {
            int mid = l + r >> 1;
            if (b[mid] >= x) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        // 映射到1, 2, ...m
        return l;
    }
}
