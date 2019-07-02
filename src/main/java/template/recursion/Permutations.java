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


}
