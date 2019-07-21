package template.two.pointer;

/**
 * @author hum
 */
public class Collision {

    /**
     * two pointers move to the middle
     * no need to scan redundant states
     *
     * @param nums   an array
     * @param target a goal
     */
    public void helper(int[] nums, int target) {
        int i = 0;
        int j = nums.length - 1;
        if (nums[i] + nums[j] > target) {
            j--;
            // do something
        } else if (nums[i] + nums[j] < target) {
            i++;
            // do something
        } else {
            // i++ or j--
        }
    }
}
