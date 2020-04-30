package template.graph;

/**
 * @author hum
 */
public class Floyd {
    int N = 105;
    int[][] dist = new int[N][N];
    int n;

    void init() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = 0x3f3f3f3f;
                }
            }
        }
        // 对每个输入再赋值dist[][]
    }

    // 算法结束后，d[a][b]表示a到b的最短距离
    // 如果存在负权边，则无解一般可以用d[a][b]判断是否大于inf/2
    void floyd() {
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
    }
}
