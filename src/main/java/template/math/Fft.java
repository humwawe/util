package template.math;

/**
 * @author hum
 */
class Fft {
  // a b 为多项次每个的系数
  // 设 F(x)=a0+a1*x+a2*x^2+…+an*x^n a保存为[a0,a1,a2,a3,...,an]
  // 返回fft乘积后的结果数组
  long[] convolute(int[] a, int[] b) {
    int m = Integer.highestOneBit(Math.max(Math.max(a.length, b.length) - 1, 1)) << 2;
    double[][] fa = fft(a, m, false);
    double[][] fb = a == b ? fa : fft(b, m, false);
    for (int i = 0; i < m; i++) {
      double nfa0 = fa[0][i] * fb[0][i] - fa[1][i] * fb[1][i];
      double nfa1 = fa[0][i] * fb[1][i] + fa[1][i] * fb[0][i];
      fa[0][i] = nfa0;
      fa[1][i] = nfa1;
    }
    fft(fa[0], fa[1], true);
    long[] ret = new long[m];
    for (int i = 0; i < m; i++) {
      ret[i] = Math.round(fa[0][i]);
    }
    return ret;
  }

  double[][] fft(int[] srcRe, int n, boolean inverse) {
    int m = srcRe.length;
    double[] dstRe = new double[n];
    double[] dstIm = new double[n];
    for (int i = 0; i < m; i++) {
      dstRe[i] = srcRe[i];
    }

    int h = Integer.numberOfTrailingZeros(n);
    for (int i = 0; i < n; i++) {
      int rev = Integer.reverse(i) >>> 32 - h;
      if (i < rev) {
        double d = dstRe[i];
        dstRe[i] = dstRe[rev];
        dstRe[rev] = d;
      }
    }

    for (int s = 2; s <= n; s <<= 1) {
      int nt = s >>> 1;
      double theta = inverse ? -2 * Math.PI / s : 2 * Math.PI / s;
      double wRe = Math.cos(theta);
      double wIm = Math.sin(theta);
      for (int j = 0; j < n; j += s) {
        double wr = 1, wi = 0;
        for (int t = j; t < j + nt; t++) {
          int jp = t + nt;
          double re = dstRe[jp] * wr - dstIm[jp] * wi;
          double im = dstRe[jp] * wi + dstIm[jp] * wr;
          dstRe[jp] = dstRe[t] - re;
          dstIm[jp] = dstIm[t] - im;
          dstRe[t] += re;
          dstIm[t] += im;
          double nwre = wr * wRe - wi * wIm;
          double nwim = wr * wIm + wi * wRe;
          wr = nwre;
          wi = nwim;
        }
      }
    }

    if (inverse) {
      for (int i = 0; i < n; i++) {
        dstRe[i] /= n;
        dstIm[i] /= n;
      }
    }

    return new double[][]{dstRe, dstIm};
  }

  void fft(double[] re, double[] im, boolean inverse) {
    int n = re.length;
    int h = Integer.numberOfTrailingZeros(n);
    for (int i = 0; i < n; i++) {
      int rev = Integer.reverse(i) >>> 32 - h;
      if (i < rev) {
        double d = re[i];
        re[i] = re[rev];
        re[rev] = d;
        d = im[i];
        im[i] = im[rev];
        im[rev] = d;
      }
    }

    for (int s = 2; s <= n; s <<= 1) {
      int nt = s >>> 1;
      double theta = inverse ? -2 * Math.PI / s : 2 * Math.PI / s;
      double wRe = Math.cos(theta);
      double wIm = Math.sin(theta);
      for (int j = 0; j < n; j += s) {
        double wr = 1, wi = 0;
        for (int t = j; t < j + nt; t++) {
          int jp = t + nt;
          double lre = re[jp] * wr - im[jp] * wi;
          double lim = re[jp] * wi + im[jp] * wr;
          re[jp] = re[t] - lre;
          im[jp] = im[t] - lim;
          re[t] += lre;
          im[t] += lim;
          double nwre = wr * wRe - wi * wIm;
          double nwim = wr * wIm + wi * wRe;
          wr = nwre;
          wi = nwim;
        }
      }
    }

    if (inverse) {
      for (int i = 0; i < n; i++) {
        re[i] /= n;
        im[i] /= n;
      }
    }
  }
}
