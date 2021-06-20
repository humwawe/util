package template.sort;

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

    public static long[] radixSort(long[] f) {
        return radixSort(f, f.length);
    }

    public static long[] radixSort(long[] f, int n) {
        long[] to = new long[n];
        {
            int[] b = new int[65537];
            for (int i = 0; i < n; i++) {
                b[1 + (int) (f[i] & 0xffff)]++;
            }
            for (int i = 1; i <= 65536; i++) {
                b[i] += b[i - 1];
            }
            for (int i = 0; i < n; i++) {
                to[b[(int) (f[i] & 0xffff)]++] = f[i];
            }
            long[] d = f;
            f = to;
            to = d;
        }
        {
            int[] b = new int[65537];
            for (int i = 0; i < n; i++) {
                b[1 + (int) (f[i] >>> 16 & 0xffff)]++;
            }
            for (int i = 1; i <= 65536; i++) {
                b[i] += b[i - 1];
            }
            for (int i = 0; i < n; i++) {
                to[b[(int) (f[i] >>> 16 & 0xffff)]++] = f[i];
            }
            long[] d = f;
            f = to;
            to = d;
        }
        {
            int[] b = new int[65537];
            for (int i = 0; i < n; i++) {
                b[1 + (int) (f[i] >>> 32 & 0xffff)]++;
            }
            for (int i = 1; i <= 65536; i++) {
                b[i] += b[i - 1];
            }
            for (int i = 0; i < n; i++) {
                to[b[(int) (f[i] >>> 32 & 0xffff)]++] = f[i];
            }
            long[] d = f;
            f = to;
            to = d;
        }
        {
            int[] b = new int[65537];
            for (int i = 0; i < n; i++) {
                b[1 + (int) (f[i] >>> 48 & 0xffff)]++;
            }
            for (int i = 1; i <= 65536; i++) {
                b[i] += b[i - 1];
            }
            for (int i = 0; i < n; i++) {
                to[b[(int) (f[i] >>> 48 & 0xffff)]++] = f[i];
            }
            long[] d = f;
            f = to;
            to = d;
        }
        return f;
    }

    public int[] radixSort2(int[] a) {
        int n = a.length;
        int[] c0 = new int[0x101];
        int[] c1 = new int[0x101];
        int[] c2 = new int[0x101];
        int[] c3 = new int[0x101];
        for (int v : a) {
            c0[(v & 0xff) + 1]++;
            c1[(v >>> 8 & 0xff) + 1]++;
            c2[(v >>> 16 & 0xff) + 1]++;
            c3[(v >>> 24 ^ 0x80) + 1]++;
        }
        for (int i = 0; i < 0xff; i++) {
            c0[i + 1] += c0[i];
            c1[i + 1] += c1[i];
            c2[i + 1] += c2[i];
            c3[i + 1] += c3[i];
        }
        int[] t = new int[n];
        for (int v : a) {
            t[c0[v & 0xff]++] = v;
        }
        for (int v : t) {
            a[c1[v >>> 8 & 0xff]++] = v;
        }
        for (int v : a) {
            t[c2[v >>> 16 & 0xff]++] = v;
        }
        for (int v : t) {
            a[c3[v >>> 24 ^ 0x80]++] = v;
        }
        return a;
    }
}
