package template.math;

/**
 * 十二重计数
 * 有n个球和m个盒子，球要全部装进盒子里
 * todo
 *
 * @author hum
 */
public class BoxAndBall {
    int mod = 998244353;
    int N = 200005;
    int n, m;


    /**
     * 1.球不同，盒子不同
     * 每个球可以随意放：m^n
     */
    long s1() {
        return qp(n, m, mod);
    }


    /**
     * 2.球不同，盒子不同，每个盒子至多装一个
     * n > m，无法满足：0
     * n <= m，n个球排列到m个位置上：A(m,n)
     */
    long s2() {
        if (n > m) {
            return 0;
        }
        return fact[m] * infact[m - n] % mod;
    }

    /**
     * 3.球不同，盒子不同，每个盒子至少装一个
     * n < m，无法满足：0
     * n <= m，先将n个球划分到m个非空集合中为第二类斯特林数：S(m,n)，在对m重排列，m!*S(m,n)
     * S(m,n) = S(m-1,n-1) + m * S(n-1,m) // 一个新球重开一个集合，或者选一个放到已有的集合中
     */
    long s3() {
        return 0;
    }


    long[] fact = new long[N];
    long[] infact = new long[N];

    void fact() {
        fact[0] = infact[0] = 1;
        for (int i = 1; i < N; i++) {
            fact[i] = fact[i - 1] * i % mod;
            infact[i] = infact[i - 1] * qp(i, mod - 2, mod) % mod;
        }
    }

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
