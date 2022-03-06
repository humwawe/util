package template.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * @author hum
 */
public class QuickSort {
  /**
   * @param q array
   * @param l 0
   * @param r len - 1
   */
  void quickSort(int q[], int l, int r) {
    if (l >= r) {
      return;
    }
    // x可以取q[l]，但是不能取q[r]
    int i = l - 1, j = r + 1, x = q[l + r >> 1];
    while (i < j) {
      do {
        i++;
      } while (q[i] < x);
      do {
        j--;
      } while (q[j] > x);
      if (i < j) {
        int tmp = q[i];
        q[i] = q[j];
        q[j] = tmp;
      }
    }
    quickSort(q, l, j);
    quickSort(q, j + 1, r);
  }

  void ruffleSort(int[] a) {
    Random get = new Random();
    for (int i = 0; i < a.length; i++) {
      int r = get.nextInt(a.length);
      int temp = a[i];
      a[i] = a[r];
      a[r] = temp;
    }
    Arrays.sort(a);
  }

  public void longRadixSort(long[] a) {
    int fr = 0;
    int to = a.length;
    int[] c0 = new int[0x101];
    int[] c1 = new int[0x101];
    int[] c2 = new int[0x101];
    int[] c3 = new int[0x101];
    int[] c4 = new int[0x101];
    int[] c5 = new int[0x101];
    int[] c6 = new int[0x101];
    int[] c7 = new int[0x101];
    c0[0] = c1[0] = c2[0] = c3[0] = c4[0] = c5[0] = c6[0] = c7[0] = fr;
    for (int i = fr; i < to; i++) {
      long v = a[i];
      c0[(int) (v & 0xff) + 1]++;
      c1[(int) (v >>> 8 & 0xff) + 1]++;
      c2[(int) (v >>> 16 & 0xff) + 1]++;
      c3[(int) (v >>> 24 & 0xff) + 1]++;
      c4[(int) (v >>> 32 & 0xff) + 1]++;
      c5[(int) (v >>> 40 & 0xff) + 1]++;
      c6[(int) (v >>> 48 & 0xff) + 1]++;
      c7[(int) (v >>> 56 ^ 0x80) + 1]++;
    }
    for (int i = 0; i < 0x100; i++) {
      c0[i + 1] += c0[i];
      c1[i + 1] += c1[i];
      c2[i + 1] += c2[i];
      c3[i + 1] += c3[i];
      c4[i + 1] += c4[i];
      c5[i + 1] += c5[i];
      c6[i + 1] += c6[i];
      c7[i + 1] += c7[i];
    }
    long[] b = new long[a.length];
    for (int i = fr; i < to; i++) {
      long v = a[i];
      b[c0[(int) (v & 0xff)]++] = v;
    }
    for (int i = fr; i < to; i++) {
      long v = b[i];
      a[c1[(int) (v >>> 8 & 0xff)]++] = v;
    }
    for (int i = fr; i < to; i++) {
      long v = a[i];
      b[c2[(int) (v >>> 16 & 0xff)]++] = v;
    }
    for (int i = fr; i < to; i++) {
      long v = b[i];
      a[c3[(int) (v >>> 24 & 0xff)]++] = v;
    }
    for (int i = fr; i < to; i++) {
      long v = a[i];
      b[c4[(int) (v >>> 32 & 0xff)]++] = v;
    }
    for (int i = fr; i < to; i++) {
      long v = b[i];
      a[c5[(int) (v >>> 40 & 0xff)]++] = v;
    }
    for (int i = fr; i < to; i++) {
      long v = a[i];
      b[c6[(int) (v >>> 48 & 0xff)]++] = v;
    }
    for (int i = fr; i < to; i++) {
      long v = b[i];
      a[c7[(int) (v >>> 56 ^ 0x80)]++] = v;
    }
  }

  public void intRadixSort(int[] a) {
    int fr = 0;
    int to = a.length;
    int[] c0 = new int[0x101];
    int[] c1 = new int[0x101];
    int[] c2 = new int[0x101];
    int[] c3 = new int[0x101];
    c0[0] = c1[0] = c2[0] = c3[0] = fr;
    for (int i = fr; i < to; i++) {
      int v = a[i];
      c0[(v & 0xff) + 1]++;
      c1[(v >>> 8 & 0xff) + 1]++;
      c2[(v >>> 16 & 0xff) + 1]++;
      c3[(v >>> 24 ^ 0x80) + 1]++;
    }
    for (int i = 0; i < 0x100; i++) {
      c0[i + 1] += c0[i];
      c1[i + 1] += c1[i];
      c2[i + 1] += c2[i];
      c3[i + 1] += c3[i];
    }
    int[] b = new int[a.length];
    for (int i = fr; i < to; i++) {
      int v = a[i];
      b[c0[v & 0xff]++] = v;
    }
    for (int i = fr; i < to; i++) {
      int v = b[i];
      a[c1[v >>> 8 & 0xff]++] = v;
    }
    for (int i = fr; i < to; i++) {
      int v = a[i];
      b[c2[v >>> 16 & 0xff]++] = v;
    }
    for (int i = fr; i < to; i++) {
      int v = b[i];
      a[c3[v >>> 24 ^ 0x80]++] = v;
    }
  }
}
