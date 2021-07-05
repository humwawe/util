package template.string;

/**
 * 将字符串看成P进制数
 *
 * @author hum
 */
public class Hash {
    // P的经验值是131或13331，取这两个值的冲突概率低
    long P = 133331;
    int N = 100;
    // h[k]存储字符串前k个字母的哈希值, p[k]存储 P^k mod (1L << 61) - 1
    long[] h = new long[N];
    long[] p = new long[N];
    long mod = (1L << 61) - 1;

    public void hash(String str) {
        // 初始化
        p[0] = 1;
        for (int i = 1; i <= str.length(); i++) {
            h[i] = mul(h[i - 1], P) + str.charAt(i - 1);
            if (h[i] >= mod) {
                h[i] -= mod;
            }
            p[i] = mul(p[i - 1], P);
        }
    }

    // 计算子串 str[l ~ r] 的哈希值，下标从1开始
    long get(int l, int r) {
        return modular(h[r] - mul(h[l - 1], p[r - l + 1]));
    }

    private long mul(long a, long b) {
        long al = a & (1L << 31) - 1, ah = a >>> 31;
        long bl = b & (1L << 31) - 1, bh = b >>> 31;
        // <2^62
        long low = al * bl;
        // < 2^62
        long mid = al * bh + bl * ah;
        // < 2^60 + 2^31 < 2^61
        long high = ah * bh + (mid >>> 31);
        // high*2^62 = high*2 (mod 2^61-1)
        return modular(modular(2 * high + low) + ((mid & (1L << 31) - 1) << 31));
    }

    private long modular(long a) {
        while (a >= mod) {
            a -= mod;
        }
        while (a < 0) {
            a += mod;
        }
        return a;
    }

}
