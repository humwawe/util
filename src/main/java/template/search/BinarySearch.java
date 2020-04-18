package template.search;

import java.util.*;

/**
 * @author hum
 */
public class BinarySearch {

    /**
     * binary search
     * no +1 or -1 when update left and right position
     *
     * @param nums   source array
     * @param target target to search
     * @return the *first* occurrence position of target
     * if want to return last, need adjust code: 1 -> left = mid and exchange 2 and 3
     */
    public int helper(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int left = 0, right = nums.length - 1;
        int mid;
        while (left + 1 < right) {
            mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                // 1
                right = mid;
            } else if (nums[mid] < target) {
                left = mid;
            } else if (nums[mid] > target) {
                right = mid;
            }
        }
        // 2
        if (nums[left] == target) {
            return left;
        }
        // 3
        if (nums[right] == target) {
            return right;
        }
        return -1;
    }

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

    // [l,r] 闭区间，从左往右返回第一个大于等于t的位置，末尾最多到r
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

    // [l,r] 闭区间，从右往左返回第一个小于等于t的位置，最小最多到0
    int upperBound(int[] a, int t, int l, int r) {
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

    // 返回插入位置
    int lowerBoundArray(Integer[] arr, int t) {
        // 自定义比较器，比较器没有返回0
        int result = Arrays.binarySearch(arr, t, (o1, o2) -> (o1.compareTo(o2) >= 0) ? 1 : -1);
        // 所有result均为负数，取反等价于 -result-1
        return (result >= 0) ? result : ~result;
    }

    int upperBoundArray(Integer[] arr, int t) {
        int result = Arrays.binarySearch(arr, t, (o1, o2) -> (o1.compareTo(o2) > 0) ? 1 : -1);
        return (result >= 0) ? result : ~result;
    }

    int lowerBoundList(List<Integer> list, int t) {
        int result = Collections.binarySearch(list, t, (o1, o2) -> (o1.compareTo(o2) >= 0) ? 1 : -1);
        return (result >= 0) ? result : ~result;
    }

    int upperBoundList(List<Integer> list, int t) {
        int result = Collections.binarySearch(list, t, (o1, o2) -> (o1.compareTo(o2) > 0) ? 1 : -1);
        return (result >= 0) ? result : ~result;
    }
}
