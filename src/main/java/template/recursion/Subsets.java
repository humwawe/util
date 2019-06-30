package template.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hum
 */
public class Subsets {
    /**
     * get nums subsets
     *
     * @param nums    a set of *distinct* integers
     * @param pos     start pos
     * @param results return results
     * @param result  tmp result
     */
    public void helper(int[] nums, int pos, List<List<Integer>> results, List<Integer> result) {
        results.add(new ArrayList<>(result));
        for (int i = pos; i < nums.length; i++) {
            result.add(nums[i]);
            helper(nums, i + 1, results, result);
            result.remove(result.size() - 1);
        }
    }

    /**
     * get nums subsets2
     * need sort first
     *
     * @param nums    a collection of integers that might contain *duplicates* num
     * @param pos     start pos
     * @param results return results
     * @param result  tmp result
     */
    public void helper2(int[] nums, int pos, List<List<Integer>> results, List<Integer> result) {
        results.add(new ArrayList<>(result));
        for (int i = pos; i < nums.length; i++) {
            if (i != pos && nums[i] == nums[i - 1]) {
                continue;
            }
            result.add(nums[i]);
            helper2(nums, i + 1, results, result);
            result.remove(result.size() - 1);
        }
    }

}
