package template.tree;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author hum
 */
public class DescartesTree {
  int N = 105;
  // 某个位置左右儿子的位置
  int[] l = new int[N];
  int[] r = new int[N];

  int n;

  // 对a进行建笛卡尔树，返回root的位置，依次按最小值划分左右子树，中序遍历为原数组
  // 区间最小值为LCA的值
  // 某个节点的父元素是某个方向第一个大于它的元素，一直朝该方向上去后的拐点是另一个方向第一个大于它的元素（有点单调栈的感觉，但可以转换为树来思考）
  int build(int[] a) {
    Arrays.fill(l, -1);
    Arrays.fill(r, -1);
    Stack<Integer> stack = new Stack<>();
    int root = -1;
    for (int i = 0; i < n; i++) {
      int last = -1;
      while (!stack.isEmpty() && a[stack.peek()] > a[i]) {
        last = stack.pop();
      }
      if (!stack.isEmpty()) {
        r[stack.peek()] = i;
      } else {
        root = i;
      }
      l[i] = last;
      stack.push(i);
    }


    // 根据左右子树建树
    //    for (int i = 0; i < n; i++) {
    //      if (l[i] != -1) {
    //        add(i, l[i]);
    //        add(l[i], i);
    //      }
    //      if (r[i] != -1) {
    //        add(i, r[i]);
    //        add(r[i], i);
    //      }
    //    }

    return root;
  }

}
