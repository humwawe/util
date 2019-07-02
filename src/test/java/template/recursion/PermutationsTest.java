package template.recursion;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PermutationsTest {
    private Permutations permutations = new Permutations();

    @Test
    void helper() {
        List<Integer> result = new ArrayList<>();
        List<List<Integer>> results = new ArrayList<>();
        int[] nums = new int[]{1, 2, 3};
        boolean[] used = new boolean[nums.length];
        permutations.helper(nums, used, results, result);
        System.out.println(results);

    }

    @Test
    void helper2() {
        List<Integer> result = new ArrayList<>();
        List<List<Integer>> results = new ArrayList<>();
        int[] nums = new int[]{1, 2, 1};
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        permutations.helper2(nums, used, results, result);
        System.out.println(results);

    }
}