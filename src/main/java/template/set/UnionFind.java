package template.set;

/**
 * @author hum
 */
public class UnionFind {
    class UnionFind1 {
        int n = 100;
        //存储每个点的祖宗节点
        int[] p = new int[n + 1];
        int[] size = new int[n + 1];

        public UnionFind1() {
            // 初始化，假定节点编号是1~n
            for (int i = 1; i <= n; i++) {
                p[i] = i;
            }
        }

        // 返回x的祖宗节点 并压缩路径
        int find(int x) {
            if (p[x] != x) {
                p[x] = find(p[x]);
            }
            return p[x];
        }

        // 合并a和b所在的两个集合：n
        void union(int a, int b) {
            if (find(a) == find(b)) {
                return;
            }
            size[find(b)] += size[find(a)];
            p[find(a)] = find(b);
        }
    }

}
