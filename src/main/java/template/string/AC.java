package template.string;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author hum
 */
public class AC {
    int N = 105;
    int L = 35;
    int[][] tr = new int[N * L][26];
    int[] cnt = new int[N * L];
    int[] next = new int[N * L];
    // 代表M长的字符串，有多少个n的字符串在长字符串中出现
    int M = 1005;
    int idx = 0;

    void insert(char[] str) {
        int p = 0;
        for (int i = 0; i < str.length; i++) {
            int t = str[i] - 'a';
            if (tr[p][t] == 0) {
                tr[p][t] = ++idx;
                p = tr[p][t];
            }
        }
        cnt[p]++;
    }

    void build() {
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < 26; i++) {
            if (tr[0][i] != 0) {
                queue.add(tr[0][i]);
            }
        }
        while (!queue.isEmpty()) {
            int t = queue.poll();
            for (int i = 0; i < 26; i++) {
                int p = tr[t][i];
                if (p == 0) {
                    tr[t][i] = tr[next[t]][i];
                } else {
                    next[p] = tr[next[t]][i];
                    queue.add(p);
                }
            }
        }
    }

    int query(char[] str) {
        int res = 0;
        for (int i = 0, j = 0; i < str.length; i++) {
            int t = str[i] - 'a';
            j = tr[j][t];
            int p = j;
            while (p != 0) {
                res += cnt[p];
                cnt[p] = 0;
                p = next[p];
            }
        }
        return res;
    }

}
