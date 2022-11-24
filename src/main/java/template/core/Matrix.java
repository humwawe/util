package template.core;

import java.util.Arrays;

/**
 * @author hum
 */
public class Matrix {
  int n, m;
  long[] mat;

  public Matrix(int n, int m) {
    this.n = n;
    this.m = m;
    mat = new long[n * m];
  }

  public Matrix(int[][] a) {
    this.n = a.length;
    this.m = a[0].length;
    mat = new long[n * m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        mat[i * m + j] = a[i][j];
      }
    }
  }

  public Matrix(long[][] a) {
    this.n = a.length;
    this.m = a[0].length;
    mat = new long[n * m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        mat[i * m + j] = a[i][j];
      }
    }
  }

  public Matrix(int[] a, int n) {
    assert a.length % n == 0;
    this.n = n;
    this.m = a.length / n;
    mat = new long[a.length];
    for (int i = 0; i < a.length; i++) {
      mat[i] = a[i];
    }
  }

  public Matrix(long[] a, int n) {
    assert a.length % n == 0;
    this.n = n;
    this.m = a.length / n;
    mat = new long[a.length];
    System.arraycopy(a, 0, mat, 0, a.length);
  }

  public Matrix(Matrix b) {
    this.n = b.n;
    this.m = b.m;
    mat = new long[n * m];
    System.arraycopy(b.mat, 0, mat, 0, n * m);
  }

  public long[][] toArray() {
    long[][] res = new long[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        res[i][j] = mat[i * m + j];
      }
    }
    return res;
  }

  public void fill(int v) {
    Arrays.fill(mat, v);
  }

  public void asStandard() {
    fill(0);
    for (int i = 0; i < mat.length; i += m + 1) {
      mat[i] = 1;
    }
  }

  public void set(int i, int j, long val) {
    mat[i * m + j] = val;
  }

  public long get(int i, int j) {
    return mat[i * m + j];
  }

  // 矩阵乘法，结果对p取模
  // (n m) * (b.n,b.m)
  public Matrix mul(Matrix b, int p) {
    assert m == b.n;
    int h = n;
    int mid = m;
    int w = b.m;
    Matrix c = new Matrix(h, w);

    for (int i = 0; i < h; i++) {
      for (int k = 0; k < mid; k++) {
        for (int j = 0; j < w; j++) {
          c.mat[i * w + j] = (c.mat[i * w + j] + mat[i * mid + k] * b.mat[k * w + j]) % p;
        }
      }
    }
    return c;
  }

  public Matrix plus(Matrix b, int p) {
    assert n == b.n;
    assert m == b.m;
    Matrix res = new Matrix(n, m);
    for (int i = 0; i < res.mat.length; i++) {
      res.mat[i] = (mat[i] + b.mat[i]) % p;
    }
    return res;
  }

  public Matrix subtract(Matrix b, int p) {
    assert n == b.n;
    assert m == b.m;
    Matrix res = new Matrix(n, m);
    for (int i = 0; i < res.mat.length; i++) {
      res.mat[i] = (mat[i] - b.mat[i] + p) % p;
    }
    return res;
  }

  public Matrix mul(Matrix a, long k, int p) {
    Matrix res = new Matrix(n, m);
    for (int i = 0; i < res.mat.length; i++) {
      res.mat[i] = a.mat[i] * k % p;
    }
    return res;
  }


  public Matrix pow(long k, int p) {
    assert n == m;
    Matrix res = new Matrix(n, m);
    res.asStandard();
    Matrix b = new Matrix(this);
    while (k > 0) {
      if ((k & 1) == 1) {
        res = res.mul(b, p);
      }
      k >>= 1;
      b = b.mul(b, p);
    }
    return res;

  }


  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        builder.append(mat[i * m + j]).append(' ');
      }
      builder.append('\n');
    }
    return builder.toString();
  }


}
