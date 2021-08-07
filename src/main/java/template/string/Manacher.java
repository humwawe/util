package template.string;

/**
 * @author hum
 */
public class Manacher {
    int N = 105;
    int[] ma = new int[N * 2];
    int[] mp = new int[N * 2];

    /**
     * abaaba
     * i:     0 1 2 3 4 5 6 7 8 9 10 11 12 13
     * ma[i]: $ # a # b # a # a # b  #  a  #
     * mp[i]: 1 1 2 1 4 1 2 7 2 1 4  1  2  1
     * 遍历 2*len(str)+2 mp[i]-1 为从i向左右拓展的结果
     */
    void manacher(char s[], int len) {
        int l = 0;
        ma[l++] = '$';
        ma[l++] = '#';
        for (int i = 0; i < len; i++) {
            ma[l++] = s[i];
            ma[l++] = '#';
        }
        int mx = 0, id = 0;
        for (int i = 0; i < l; i++) {
            mp[i] = mx > i ? Math.min(mp[2 * id - i], mx - i) : 1;
            while (i + mp[i] < l && i - mp[i] >= 0 && ma[i + mp[i]] == ma[i - mp[i]]) {
                mp[i]++;
            }
            if (i + mp[i] > mx) {
                mx = i + mp[i];
                id = i;
            }
        }
    }


}
