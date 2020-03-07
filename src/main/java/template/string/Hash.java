package template.string;

/**
 * 将字符串看成P进制数
 *
 * @author hum
 */
public class Hash {
    // P的经验值是131或13331，取这两个值的冲突概率低
    int P = 131;
    int n = 100;
    // h[k]存储字符串前k个字母的哈希值, p[k]存储 P^k mod 1e9+7
    long[] h = new long[n];
    long[] p = new long[n];
    int mod = (int) (1e9 + 7);

    public Hash() {
        // 初始化
        p[0] = 1;
        String str = "aa";
        for (int i = 1; i <= str.length(); i++) {
            h[i] = (h[i - 1] * P + str.charAt(i - 1)) % mod;
            p[i] = p[i - 1] * P % mod;
        }
    }

    // 计算子串 str[l ~ r] 的哈希值，下标从1开始
    int get(int l, int r) {
        return (int) ((h[r] - h[l - 1] * p[r - l + 1] % mod + mod) % mod);
    }

}
