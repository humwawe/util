package template.recursion;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SubsetsTest {
  private Subsets subsets = new Subsets();

  @Test
  void testSubsets() {
    List<Integer> result = new ArrayList<>();
    List<List<Integer>> results = new ArrayList<>();
    int[] nums = new int[]{1, 2, 3};
    subsets.helper(nums, 0, results, result);
    System.out.println(results);
  }

  @Test
  void testSubsets2() {
    List<Integer> result = new ArrayList<>();
    List<List<Integer>> results = new ArrayList<>();
    int[] nums = new int[]{1, 2, 2, 2};
    Arrays.sort(nums);
    subsets.helper2(nums, 0, results, result);
    System.out.println(results);
  }

  @Test
  void testSubsets5() {
    List<Integer> result = new ArrayList<>();
    List<List<Integer>> results = new ArrayList<>();
    int[] nums = new int[]{1, 1, 1, 2};
    Arrays.sort(nums);
    subsets.helper5(nums, 0, results, result);
    System.out.println(results);
  }
}