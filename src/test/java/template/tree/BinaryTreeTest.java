package template.tree;

import org.junit.jupiter.api.Test;


class BinaryTreeTest {

  @Test
  void preAndPostBuildTree() {
    BInaryTreeCodec binaryTree = new BInaryTreeCodec();
    TreeNode treeNode = binaryTree.deserializeBfs("1,2,3,#,#,4,5");
    System.out.println(binaryTree.serializeBfs(treeNode));
    StringBuilder sb = new StringBuilder();
    binaryTree.serializePre(treeNode, sb);
    binaryTree.removeLastNull(sb);
    System.out.println(sb.toString());
    TreeNode treeNode1 = binaryTree.deserializePre(sb.toString());
    sb = new StringBuilder();
    binaryTree.serializePost(treeNode1, sb);
    binaryTree.removeFirstNull(sb);
    System.out.println(sb.toString());
    System.out.println("".split(",").length);
  }
}