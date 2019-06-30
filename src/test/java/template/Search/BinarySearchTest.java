package template.Search;

import org.junit.jupiter.api.Test;


class BinarySearchTest {
    BinarySearch binarySearch = new BinarySearch();

    @Test
    void helper() {
        int[] nums = new int[]{0, 1, 2, 2, 2, 3, 4, 5};
        System.out.println(binarySearch.helper(nums, 0));
    }
}