package template.stack;

import java.util.Stack;

/**
 * @author hum
 */
public class MonotonicStack {

  // 数组模拟栈操作
  private void op() {
    // tt为栈顶
    // 栈初始化： int[] stk = new int[N]; int tt = 0;
    // 向栈顶插入一个数：stk[++tt] = x;
    // 从栈顶弹出一个数：tt--;
    // 栈顶的值：stk[tt];
    // 判断栈是否为空 if (tt > 0)
  }

  // 单调栈
  public void helper(int[] nums) {

    int len = nums.length;
    // 左边第一个比当前元素小的位置，没有则为-1，取开区间
    int[] left = new int[len];
    // 右边第一个比当前元素小的位置，没有则为len
    int[] right = new int[len];

    // 当前元素范围为：right[i] - left[i] - 1
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < len; i++) {
      while (!stack.isEmpty() && nums[i] <= nums[stack.peek()]) {
        stack.pop();
      }
      if (stack.isEmpty()) {
        left[i] = -1;
      } else {
        left[i] = stack.peek();
      }
      stack.push(i);
    }
    stack.clear();
    for (int i = len - 1; i >= 0; i--) {
      while (!stack.isEmpty() && nums[i] <= nums[stack.peek()]) {
        stack.pop();
      }
      if (stack.isEmpty()) {
        right[i] = len;
      } else {
        right[i] = stack.peek();
      }
      stack.push(i);
    }
  }


}
