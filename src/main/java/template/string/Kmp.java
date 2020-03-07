package template.string;

/**
 * @author hum
 */
public class Kmp {

    /**
     * next[] 的含义：x[i-next[i]...i-1]=x[0...next[i]-1]
     * next[i] 为满足 x[i-z...i-1]=x[0...z-1] 的最大 z 值（就是 x 的自身匹配）
     */
    void kmpPre(char x[], int m, int[] next) {
        int i, j;
        j = next[0] = -1;
        i = 0;
        while (i < m) {
            while (-1 != j && x[i] != x[j]) {
                j = next[j];
            }
            next[++i] = ++j;
        }
    }

    /**
     * kmpNext[i] 的意思:next'[i]=next[next[...[next[i]]]](直到 next'[i]<0 或者 x[next'[i]]!=x[i])
     */
    void kmpPre2(char x[], int m, int[] kmpNext) {
        int i, j;
        j = kmpNext[0] = -1;
        i = 0;
        while (i < m) {
            while (-1 != j && x[i] != x[j]) {
                j = kmpNext[j];
            }
            if (x[++i] == x[++j]) {
                kmpNext[i] = kmpNext[j];
            } else {
                kmpNext[i] = j;
            }
        }
    }

    public int helper() {
        // y[]是长文本，x[]是模式串
        char y[] = new char[]{'a', 'b', 'c', 'b', 'c', 'b', 'c'};
        char x[] = new char[]{'b', 'c', 'b'};
        int n = y.length;
        int m = x.length;

        int[] next = new int[10];
        int i, j;
        int ans = 0;
        kmpPre(x, m, next);
        i = j = 0;
        while (i < n) {
            while (-1 != j && y[i] != x[j]) {
                j = next[j];
            }
            i++;
            j++;
            if (j >= m) {
                // 匹配成功
                System.out.println(i);
                ans++;
                j = next[j];
            }
        }
        return ans;
    }

}
