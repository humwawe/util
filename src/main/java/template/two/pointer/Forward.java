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


  public void helper2() {
    int len = 10;
    // 左j 右i
    for (int i = 0, j = 0; i < len; i++) {
      while (j < i && check(i, j)) {
        j++;
      }
      // 具体问题的逻辑
    }
  }

  private boolean check(int i, int j) {
    return true;
  }
}
