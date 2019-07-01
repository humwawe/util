package template.search;

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
}
