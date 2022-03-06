package template.queue;

/**
 * @author hum
 */
public class Queue {
  // hh 表示队头，tt表示队尾
  int N = 10;
  int[] q = new int[N];
  int hh = 0, tt = -1;

  // 向队尾插入一个数
  void insert(int x) {
    q[++tt] = x;
  }

  // 从队头弹出一个数
  void poolFirst() {
    hh++;
  }

  // 从队尾弹出一个数
  void poolLast() {
    tt--;
  }

  // 队头的值
  int peekFirst() {
    return q[hh];
  }

  // 判断队列是否为空
  boolean isEmpty() {
    return hh > tt;
  }

}
