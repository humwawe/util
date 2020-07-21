package template.tree;

import template.common.TreeNode;

import java.util.*;

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

    String serializeBfs(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        int cnt = 0;
        queue.add(root);
        // 记录还有多少个非空节点，保证末尾不出现多余#
        cnt++;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                cnt--;
                sb.append(node.val).append(",");
                if (node.left != null) {
                    queue.add(node.left);
                    cnt++;
                } else {
                    queue.add(null);
                }
                if (node.right != null) {
                    queue.add(node.right);
                    cnt++;
                } else {
                    queue.add(null);
                }
            } else {
                if (cnt != 0) {
                    sb.append("#,");
                }
            }
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    String serializeBfs2(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        // 记录还有多少个非空节点，保证末尾不出现多余#
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                sb.append(node.val).append(",");
                queue.add(node.left);
                queue.add(node.right);
            } else {
                sb.append("#,");
            }
        }
        removeLastNull(sb);
        return sb.toString();
    }

    public TreeNode deserializeBfs(String data) {
        String[] values = data.split(",");
        List<TreeNode> list = new ArrayList<>();
        TreeNode head = createNode(values[0]);
        list.add(head);
        int idx = 0;
        int valueIndex = 1;
        while (idx < list.size()) {
            TreeNode root = list.get(idx++);
            if (valueIndex < values.length) {
                root.left = createNode(values[valueIndex++]);
            }
            if (valueIndex < values.length) {
                root.right = createNode(values[valueIndex++]);
            }
            if (root.left != null) {
                list.add(root.left);
            }
            if (root.right != null) {
                list.add(root.right);
            }
        }
        return head;
    }

    private TreeNode createNode(String str) {
        if (str.equals("#")) {
            return null;
        }
        int integer = Integer.parseInt(str);
        return new TreeNode(integer);
    }

    void serializePre(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("#,");
            return;
        }
        sb.append(root.val).append(",");
        serializePre(root.left, sb);
        serializePre(root.right, sb);
    }

    void removeLastNull(StringBuilder sb) {
        String[] split = sb.toString().split(",");
        int len = split.length;
        while (split[len - 1].equals("#")) {
            len--;
        }
        sb.setLength(0);
        for (int i = 0; i < len; i++) {
            sb.append(split[i]).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
    }

    public TreeNode deserializePre(String data) {
        String[] values = data.split(",");
        List<String> list = new LinkedList<>(Arrays.asList(values));
        return deserializePreHelper(list);
    }

    private TreeNode deserializePreHelper(List<String> list) {
        if (list.size() == 0) {
            return null;
        }
        if (list.get(0).equals("#")) {
            list.remove(0);
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(list.get(0)));
        list.remove(0);
        root.left = deserializePreHelper(list);
        root.right = deserializePreHelper(list);
        return root;
    }


}
