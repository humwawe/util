package template.stack;

/**
 * @author hum
 */
public class Stack {

    // 数组模拟栈操作
    private void op() {
        // tt为栈顶
        // 栈初始化： int[] stk = new int[N]; int tt = 0;
        // 向栈顶插入一个数：stk[++tt] = x;
        // 从栈顶弹出一个数：tt--;
        // 栈顶的值：stk[tt];
        // 判断栈是否为空 if (tt > 0)
    }

    // 单调栈
    public void helper() {
        int tt = 0;
        int n = 10;
        int[] stk = new int[10];
        // 找出每个数离它最近的比它大/小的数
        for (int i = 1; i <= n; i++) {
            while (tt > 0 && check(stk[tt], i)) {
                tt--;
            }
            stk[++tt] = i;
        }
    }

    private boolean check(int top, int i) {
        return true;
    }


}
