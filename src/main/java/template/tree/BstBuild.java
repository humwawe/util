package template.tree;

import template.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 可以从层次，前序，后序构建bst，因为bst的大小关系，不需要多余的空节点
 *
 * @author hum
 */
public class BstBuild {
    TreeNode bfsBuildBstTree(int[] bfsOrder) {
        List<Integer> list = new ArrayList<>();
        for (int i : bfsOrder) {
            list.add(i);
        }
        return bfsBuildBstTreeHelper(list);
    }

    private TreeNode bfsBuildBstTreeHelper(List<Integer> list) {
        if (list.size() == 0) {
            return null;
        }
        TreeNode root = new TreeNode(list.get(0));
        List<Integer> l = new ArrayList<>();
        List<Integer> r = new ArrayList<>();
        // 小的放左边，大的放右边
        for (int i : list) {
            if (i < root.val) {
                l.add(i);
            } else if (i > root.val) {
                r.add(i);
            }
        }
        root.left = bfsBuildBstTreeHelper(l);
        root.right = bfsBuildBstTreeHelper(r);
        return root;
    }

    // 全局idx
    int idx = 0;

    TreeNode preBuildBstTree(int[] preOrder) {
        int len = preOrder.length;
        return preBuildBstTreeHelper(preOrder, Integer.MAX_VALUE);
    }

    private TreeNode preBuildBstTreeHelper(int[] preOrder, int bound) {
        if (idx == preOrder.length || preOrder[idx] > bound) {
            return null;
        }
        TreeNode root = new TreeNode(preOrder[idx]);
        idx++;
        root.left = preBuildBstTreeHelper(preOrder, root.val);
        root.right = preBuildBstTreeHelper(preOrder, bound);
        return root;
    }


    TreeNode preBuildBstTree2(int[] preOrder) {
        int len = preOrder.length;
        return preBuildBstTreeHelper2(preOrder, 0, len - 1);
    }

    private TreeNode preBuildBstTreeHelper2(int[] preOrder, int cur, int end) {
        if (cur == -1 || cur > end) {
            return null;
        }
        TreeNode root = new TreeNode(preOrder[cur]);
        int left = -1;
        if (cur + 1 < preOrder.length && preOrder[cur] > preOrder[cur + 1]) {
            left = cur + 1;
        }
        int right = end + 1;
        for (int i = cur + 1; i < preOrder.length; i++) {
            if (preOrder[i] > preOrder[cur]) {
                right = i;
                break;
            }
        }
        root.left = preBuildBstTreeHelper2(preOrder, left, right - 1);
        root.right = preBuildBstTreeHelper2(preOrder, right, end);
        return root;
    }


    TreeNode postBuildBstTree(int[] postOrder) {
        int len = postOrder.length;
        idx = len - 1;
        return postBuildBstTreeHelper(postOrder, Integer.MIN_VALUE);
    }

    private TreeNode postBuildBstTreeHelper(int[] postOrder, int bound) {
        if (idx < 0 || postOrder[idx] < bound) {
            return null;
        }
        TreeNode root = new TreeNode(postOrder[idx]);
        idx--;
        root.right = postBuildBstTreeHelper(postOrder, root.val);
        root.left = postBuildBstTreeHelper(postOrder, bound);
        return root;
    }

    TreeNode postBuildBstTree2(int[] postOrder) {
        int len = postOrder.length;
        return postBuildBstTreeHelper2(postOrder, 0, len - 1);
    }

    private TreeNode postBuildBstTreeHelper2(int[] postOrder, int l, int cur) {
        if (cur == -1 || cur < l) {
            return null;
        }
        TreeNode root = new TreeNode(postOrder[cur]);
        int left = -1;
        for (int i = cur - 1; i >= 0; i--) {
            if (postOrder[i] < postOrder[cur]) {
                left = i;
                break;
            }
        }
        int right = -1;
        if (cur - 1 >= 0 && postOrder[cur - 1] > postOrder[cur]) {
            right = cur - 1;
        }
        root.left = postBuildBstTreeHelper2(postOrder, l, left);
        root.right = postBuildBstTreeHelper2(postOrder, left + 1, right);
        return root;
    }

}
