package template.set;

/**
 * @author hum
 */
public class Enumeration {
    // 枚举子集，sub变为0后，(sub - 1) & st => -1 & st => st
    // 如果想要0的话可以考虑do-while(sub!=st)
    void sub(int st) {
        for (int sub = st; sub > 0; sub = (sub - 1) & st) {
            System.out.println(sub);
        }
    }

    // 枚举从n个物品中选k个
    // 最小的子集是(1<<k)-1，用它作为初始值
    // 1.求出最低位的1开始连续的1的区间，x&-x的值就是将最低位的1独立出来的值
    // 2.将这一区间全部变为0，并将区间最左侧的0变为1
    // 3.将第1步取出的区间右移，知道剩下的1的个数少了1个
    // 4.将第2步和第3步的结果按位取或
    void sub2(int n, int k) {
        int comb = (1 << k) - 1;
        while (comb < 1 << n) {
            System.out.println(comb);
            int x = comb & -comb;
            int y = comb + x;
            comb = ((comb & ~y) / x >> 1) | y;
        }
    }
}
