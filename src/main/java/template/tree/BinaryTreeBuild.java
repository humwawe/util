package template.tree;

import template.common.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hum
 */
public class BinaryTreeBuild {
  Map<Integer, Integer> idxMap = new HashMap<>();

  TreeNode preAndInBuildTree(int[] preOrder, int[] inOrder) {
    int len = preOrder.length;
    for (int i = 0; i < len; i++) {
      idxMap.put(inOrder[i], i);
    }
    return preAndInBuildTreeHelper(preOrder, inOrder, 0, len - 1, 0, len - 1);
  }

  private TreeNode preAndInBuildTreeHelper(int[] preOrder, int[] inOrder, int i1, int j1, int i2, int j2) {
    if (i1 > j1) {
      return null;
    }
    TreeNode root = new TreeNode(preOrder[i1]);
    int idx = idxMap.get(preOrder[i1]);
    int len = idx - i2;
    root.left = preAndInBuildTreeHelper(preOrder, inOrder, i1 + 1, i1 + len, i2, idx - 1);
    root.right = preAndInBuildTreeHelper(preOrder, inOrder, i1 + len + 1, j1, idx + 1, j2);
    return root;
  }

  TreeNode postAndInBuildTree(int[] inOrder, int[] PostOrder) {
    int len = inOrder.length;
    for (int i = 0; i < len; i++) {
      idxMap.put(inOrder[i], i);
    }
    return postAndInBuildTreeHelper(PostOrder, inOrder, 0, len - 1, 0, len - 1);
  }

  private TreeNode postAndInBuildTreeHelper(int[] PostOrder, int[] inOrder, int i1, int j1, int i2, int j2) {
    if (i1 > j1) {
      return null;
    }
    TreeNode root = new TreeNode(PostOrder[j1]);
    int idx = idxMap.get(PostOrder[j1]);
    int len = idx - i2;
    root.left = postAndInBuildTreeHelper(PostOrder, inOrder, i1, i1 + len - 1, i2, idx - 1);
    root.right = postAndInBuildTreeHelper(PostOrder, inOrder, i1 + len, j1 - 1, idx + 1, j2);
    return root;
  }

  TreeNode preAndPostBuildTree(int[] preOrder, int[] postOrder) {
    int len = preOrder.length;
    for (int i = 0; i < len; i++) {
      idxMap.put(postOrder[i], i);
    }
    return preAndPostBuildTreeHelper(preOrder, postOrder, 0, len - 1, 0, len - 1);
  }

  private TreeNode preAndPostBuildTreeHelper(int[] preOrder, int[] postOrder, int i1, int j1, int i2, int j2) {
    if (i1 > j1) {
      return null;
    }
    TreeNode root = new TreeNode(preOrder[i1]);
    if (i1 == j1) {
      return root;
    }
    int idx = idxMap.get(preOrder[i1 + 1]);
    int len = idx - i2;
    root.left = preAndPostBuildTreeHelper(preOrder, postOrder, i1 + 1, i1 + len + 1, i2, idx);
    root.right = preAndPostBuildTreeHelper(preOrder, postOrder, i1 + len + 2, j1, idx + 1, j2 - 1);
    return root;
  }


}
