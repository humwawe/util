package template.math;

/**
 * @author hum
 */
public class Gcd {
    int gcd(int a, int b) {
        return b != 0 ? gcd(b, a % b) : a;
    }
}
