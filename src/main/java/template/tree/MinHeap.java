package template.tree;

import java.util.Arrays;

/**
 * @author PF-2CRL0N
 */
public class MinHeap {
  public int[] a;
  public int[] map;
  public int[] imap;
  public int n;
  public int pos;
  public static int INF = Integer.MAX_VALUE;

  public MinHeap(int m) {
    n = m + 2;
    a = new int[n];
    map = new int[n];
    imap = new int[n];
    Arrays.fill(a, INF);
    Arrays.fill(map, -1);
    Arrays.fill(imap, -1);
    pos = 1;
  }

  public int add(int ind, int x) {
    int ret = imap[ind];
    if (imap[ind] < 0) {
      a[pos] = x;
      map[pos] = ind;
      imap[ind] = pos;
      pos++;
      up(pos - 1);
    }
    return ret != -1 ? a[ret] : x;
  }

  public int update(int ind, int x) {
    int ret = imap[ind];
    if (imap[ind] < 0) {
      a[pos] = x;
      map[pos] = ind;
      imap[ind] = pos;
      pos++;
      up(pos - 1);
    } else {
      int o = a[ret];
      a[ret] = x;
      up(ret);
      down(ret);
      //				if(a[ret] > o){
      //					up(ret);
      //				}else{
      //					down(ret);
      //				}
    }
    return x;
  }

  public int get(int ind) {
    int ret = imap[ind];
    if (ret < 0) {
      return ret;
    }
    return a[ret];
  }

  public int remove(int ind) {
    if (pos == 1) {
      return INF;
    }
    if (imap[ind] == -1) {
      return INF;
    }

    pos--;
    int rem = imap[ind];
    int ret = a[rem];
    map[rem] = map[pos];
    imap[map[pos]] = rem;
    imap[ind] = -1;
    a[rem] = a[pos];
    a[pos] = INF;
    map[pos] = -1;

    up(rem);
    down(rem);
    return ret;
  }

  public int min() {
    return a[1];
  }

  public int argmin() {
    return map[1];
  }

  public int size() {
    return pos - 1;
  }

  private void up(int cur) {
    for (int c = cur, p = c >>> 1; p >= 1 && a[p] > a[c]; c >>>= 1, p >>>= 1) {
      int d = a[p];
      a[p] = a[c];
      a[c] = d;
      int e = imap[map[p]];
      imap[map[p]] = imap[map[c]];
      imap[map[c]] = e;
      e = map[p];
      map[p] = map[c];
      map[c] = e;
    }
  }

  private void down(int cur) {
    for (int c = cur; 2 * c < pos; ) {
      int b = a[2 * c] < a[2 * c + 1] ? 2 * c : 2 * c + 1;
      if (a[b] < a[c]) {
        int d = a[c];
        a[c] = a[b];
        a[b] = d;
        int e = imap[map[c]];
        imap[map[c]] = imap[map[b]];
        imap[map[b]] = e;
        e = map[c];
        map[c] = map[b];
        map[b] = e;
        c = b;
      } else {
        break;
      }
    }
  }

}