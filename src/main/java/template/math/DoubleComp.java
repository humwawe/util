package template.math;

/**
 * @author hum
 */
public class DoubleComp {
  double eps = 1e-8;

  void doubleComp(double a, double b) {
    // a == b
    // Math.abs(a-b) < eps
    boolean aEqB = sgn(a - b) == 0;
    // a != b
    // Math.abs(a-b) > eps
    boolean aNeqB = sgn(a - b) != 0;
    // a < b
    // a – b < -eps
    boolean aLeB = sgn(a - b) < 0;
    // a <= b
    // a – b < eps
    boolean aLeEqB = sgn(a - b) <= 0;
    // a > b
    // a – b > eps
    boolean aLaB = sgn(a - b) > 0;
    // a >= b
    // a – b > -eps
    boolean aLaEqB = sgn(a - b) >= 0;
  }

  int cmp(double a, double b) {
    if (Math.abs(a - b) < eps) {
      return 0;
    }
    if (a < b) {
      return -1;
    }
    return 1;
  }


  int sgn(double a) {
    return a < -eps ? -1 : a < eps ? 0 : 1;
  }
}
