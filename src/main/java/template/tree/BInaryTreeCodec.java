package template.tree;

import template.common.TreeNode;

import java.util.*;

/**
 * 序列化反序列化
 * 前序和层次都是可以常规把空也当做一个元素，最后序列化的结果去掉末尾多余的#，后序则去掉前面的#
 * 中序遍历无法反序列化，因为找不到根节点
 *
 * @author hum
 */
public class BInaryTreeCodec {

    void removeLastNull(StringBuilder sb) {
        String[] split = sb.toString().split(",");
        int len = split.length;
        while (len >= 1 && split[len - 1].equals("#")) {
            len--;
        }
        sb.setLength(0);
        if (len == 0) {
            return;
        }
        for (int i = 0; i < len; i++) {
            sb.append(split[i]).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
    }

    void removeFirstNull(StringBuilder sb) {
        String[] split = sb.toString().split(",");
        int len = split.length;
        // sb以","开头，分割后第一个字符为空，跳过
        int idx = 1;
        while (idx < len && split[idx].equals("#")) {
            idx++;
        }
        sb.setLength(0);
        if (idx == len) {
            return;
        }
        for (int i = idx; i < len; i++) {
            sb.append(split[i]).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
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


    // data 为空会报错
    public TreeNode deserializePre(String data) {
        String[] values = data.split(",");
        List<String> list = new LinkedList<>(Arrays.asList(values));
        return deserializePreHelper(list);
    }

    private TreeNode deserializePreHelper(List<String> list) {
        if (list.size() == 0) {
            return null;
        }
        String s = list.remove(0);
        if (s.equals("#")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(s));
        root.left = deserializePreHelper(list);
        root.right = deserializePreHelper(list);
        return root;
    }


    void serializePost(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(",#");
            return;
        }
        serializePost(root.left, sb);
        serializePost(root.right, sb);
        sb.append(",").append(root.val);
    }


    public TreeNode deserializePost(String data) {
        String[] values = data.split(",");
        LinkedList<String> list = new LinkedList<>(Arrays.asList(values));
        return deserializePostHelper(list);
    }

    private TreeNode deserializePostHelper(LinkedList<String> list) {
        if (list.size() == 0) {
            return null;
        }
        String s = list.removeLast();
        if (s.equals("#")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(s));
        root.right = deserializePostHelper(list);
        root.left = deserializePostHelper(list);
        return root;
    }


}
