package template.search;

import java.util.function.DoubleUnaryOperator;
import java.util.function.IntUnaryOperator;

/**
 * @author hum
 */
public class TernarySearch {

  // [l,r] 中找第一个最大的位置
  // op(l)<..<op(res)=op(res+1)=...=op(res+k)>...>op(r)
  int ternarySearch(IntUnaryOperator op, int l, int r) {
    while (r - l > 2) {
      int k = (r - l) / 3;
      int ml = l + k;
      int mr = r - k;
      if (op.applyAsInt(ml) < op.applyAsInt(mr)) {
        l = ml;
      } else {
        r = mr;
      }
    }
    while (l < r) {
      if (op.applyAsInt(l) >= op.applyAsInt(r)) {
        r--;
      } else {
        l++;
      }
    }
    return l;
  }

  double ternarySearch(DoubleUnaryOperator op, double l, double r) {
    final double eps = 1e-6;
    while (r - l > eps) {
      double k = (r - l) / 3;
      double ml = l + k;
      double mr = r - k;
      if (op.applyAsDouble(ml) < op.applyAsDouble(mr)) {
        l = ml;
      } else {
        r = mr;
      }
    }
    return l;
  }

}