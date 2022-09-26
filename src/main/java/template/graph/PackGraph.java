package template.graph;

import java.util.Arrays;

/**
 * @author PF-2CRL0N
 */
public class PackGraph {

  public int[][] parents(int[][] g, int root) {
    int n = g.length;
    int[] par = new int[n];
    Arrays.fill(par, -1);

    int[] depth = new int[n];
    depth[0] = 0;

    int[] q = new int[n];
    q[0] = root;
    for (int p = 0, r = 1; p < r; p++) {
      int cur = q[p];
      for (int nex : g[cur]) {
        if (par[cur] != nex) {
          q[r++] = nex;
          par[nex] = cur;
          depth[nex] = depth[cur] + 1;
        }
      }
    }
    return new int[][]{par, q, depth};
  }

  public int[][] packU(int n, int[][] ft) {
    int[][] g = new int[n][];
    int[] p = new int[n];
    for (int[] u : ft) {
      p[u[0]]++;
      p[u[1]]++;
    }
    for (int i = 0; i < n; i++) {
      g[i] = new int[p[i]];
    }
    for (int[] u : ft) {
      g[u[0]][--p[u[0]]] = u[1];
      g[u[1]][--p[u[1]]] = u[0];
    }
    return g;
  }

  public int[][] packU(int n, int[] from, int[] to) {
    return packU(n, from, to, from.length);
  }

  public int[][] packU(int n, int[] from, int[] to, int sup) {
    int[][] g = new int[n][];
    int[] p = new int[n];
    for (int i = 0; i < sup; i++) {
      p[from[i]]++;
    }
    for (int i = 0; i < sup; i++) {
      p[to[i]]++;
    }
    for (int i = 0; i < n; i++) {
      g[i] = new int[p[i]];
    }
    for (int i = 0; i < sup; i++) {
      g[from[i]][--p[from[i]]] = to[i];
      g[to[i]][--p[to[i]]] = from[i];
    }
    return g;
  }
}
