package template.math;

/**
 * @author hum
 */
public class QuickMath {
    // 求 m^k mod p，时间复杂度 O(logk)
    int qp(int m, int k, int p) {
        long res = 1 % p, t = m;
        while (k > 0) {
            if ((k & 1) == 1) {
                // 若res,t,p都是同一量级，比如int或long，则直接算可能越界
                // 可以使用在mod下的乘积 res = qm(res, t, p)
                res = res * t % p;
            }
            // t = qm(t, t, p)
            t = t * t % p;
            k >>= 1;
        }
        return (int) res % p;
    }


    // 求 a*b mod p
    long qm(long a, long b, long p) {
        long ans = 0;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans = (ans + a) % p;
            }
            a = (a << 1) % p;
            b = b >> 1;
        }
        return ans;
    }

}
