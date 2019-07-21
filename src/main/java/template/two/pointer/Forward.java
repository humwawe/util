package template.two.pointer;

/**
 * @author hum
 */
public class Forward {
    /**
     * two pointer
     * slow pointer still travers in turn
     * fast pointer no need to come back
     * like a window but window's length is different and always changes
     * <p>
     * another two forward pointer like this
     * slow =slow.next
     * fast =fast.next.next
     *
     * @param len length
     */
    public void helper(int len) {
        int i, j = 0;
        for (i = 0; i < len; i++) {
            while (j < len) {
                // some condition
                boolean condition = false;
                if (condition) {
                    // do something
                    j++;
                } else {
                    break;
                }
            }
            // do something
        }
    }
}
