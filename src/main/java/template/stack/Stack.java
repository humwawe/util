package template.stack;

/**
 * @author hum
 */
public class Stack {
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
