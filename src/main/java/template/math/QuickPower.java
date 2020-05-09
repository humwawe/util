package template.math;

/**
 * 求 m^k mod p，时间复杂度 O(logk)。
 *
 * @author hum
 */
public class QuickPower {
    int qp(int m, int k, int p) {
        long res = 1 % p, t = m;
        while (k > 0) {
            if ((k & 1) == 1) {
                res = res * t % p;
            }
            t = t * t % p;
            k >>= 1;
        }
        return (int) res % p;
    }
}
