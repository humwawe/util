package template.tree;

import org.junit.jupiter.api.Test;
import template.common.TreeNode;

class BinaryTreeTest {

    @Test
    void preAndPostBuildTree() {
        BinaryTreeBuild binaryTree = new BinaryTreeBuild();
        System.out.println(binaryTree.preAndPostBuildTree(new int[]{1, 2}, new int[]{2, 1}));
        TreeNode treeNode = binaryTree.deserializeBfs("5,4,7,3,#,2,#,-1,#,9");
        System.out.println(binaryTree.serializeBfs(treeNode));
        TreeNode treeNode1 = binaryTree.deserializePre("1,2,#,4,#,#,3");
        StringBuilder sb = new StringBuilder();
        binaryTree.serializePre(treeNode1, sb);
        binaryTree.serializePreHelper(sb);
        System.out.println(sb.toString());

    }
}