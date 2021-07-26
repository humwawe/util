package template.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hum
 */
public class Permutations {

    /**
     * get nums permutations
     *
     * @param nums    a set of *distinct* integers
     * @param used    if the position is used
     * @param results return results
     * @param result  tmp result
     */
    public void helper(int[] nums, boolean[] used, List<List<Integer>> results, List<Integer> result) {
        if (nums.length == result.size()) {
            results.add(new ArrayList<>(result));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            result.add(nums[i]);
            used[i] = true;
            helper(nums, used, results, result);
            used[i] = false;
            result.remove(result.size() - 1);
        }
    }

    int n = 10;
    int[] nums = new int[n];

    // 枚举nums的全排列，无顺序
    private void helper1(int cur) {
        if (cur == n) {
            // nums为一个排列
            return;
        }
        for (int i = cur; i < n; i++) {
            swap(cur, i);
            helper1(cur + 1);
            swap(cur, i);
        }
    }

    private void swap(int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    /**
     * get nums permutations
     * record last used num
     * need sort first
     *
     * @param nums    a collection of integers that might contain *duplicates* num
     * @param used    if the position is used
     * @param results return results
     * @param result  tmp result
     */
    public void helper2(int[] nums, boolean[] used, List<List<Integer>> results, List<Integer> result) {
        if (nums.length == result.size()) {
            results.add(new ArrayList<>(result));
            return;
        }
        Integer lastUsed = null;
        for (int i = 0; i < nums.length; i++) {
            if (used[i] || lastUsed != null && lastUsed.equals(nums[i])) {
                continue;
            }
            result.add(nums[i]);
            used[i] = true;
            helper2(nums, used, results, result);
            used[i] = false;
            result.remove(result.size() - 1);
            lastUsed = nums[i];
        }
    }

    // nums的下一个排列，len表示nums的长度
    public void nextPermutation(int[] nums, int len) {
        int i = len - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            for (int j = len - 1; j > i; j--) {
                if (nums[j] > nums[i]) {
                    int temp = nums[j];
                    nums[j] = nums[i];
                    nums[i] = temp;
                    break;
                }
            }
        }
        int left = i + 1;
        int right = len - 1;
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
