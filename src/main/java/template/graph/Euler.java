package template.graph;


import java.util.ArrayList;
import java.util.List;

/**
 * @author hum
 */
public class Euler {
    int N = 105;
    int M = 205;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    boolean[] f = new boolean[M];
    int idx;
    // 保存欧拉回路结果
    List<Integer> list = new ArrayList<>();

    void dfs(int u) {
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (f[i]) {
                f[i] = false;
                f[i ^ 1] = false;
                dfs(j);
                list.add(j);
            }
        }
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        f[idx] = true;
        h[a] = idx++;
        e[idx] = a;
        ne[idx] = h[b];
        f[idx] = true;
        h[b] = idx++;
    }

}
