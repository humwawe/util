package template.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hum
 */
public class Dfs {
  /**
   * preOrder
   *
   * @param root   root of tree
   * @param result preOrder result
   */
  public void traverse(TreeNode root, List<Integer> result) {
    if (root == null) {
      return;
    }
    result.add(root.val);
    traverse(root.left, result);
    traverse(root.right, result);
  }

  /**
   * preOrder
   * divide & conquer  has return value
   * return type may complicated and customize
   *
   * @param root root of tree
   * @return preOrder result
   */
  public List<Integer> traversal2(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) {
      return result;
    }

    // divide
    List<Integer> left = traversal2(root.left);
    List<Integer> right = traversal2(root.right);

    // conquer
    result.add(root.val);
    result.addAll(left);
    result.addAll(right);
    return result;
  }

}
