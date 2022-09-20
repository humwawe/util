package template.string;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author PF-2CRL0N
 */
public class Hash2Mod {
  static final long multiplier = 131;
  static final Random rnd = new Random();
  static final int mod1 = BigInteger.valueOf((int) (1e9 + rnd.nextInt((int) 1e9))).nextProbablePrime().intValue();
  static final int mod2 = BigInteger.valueOf((int) (1e9 + rnd.nextInt((int) 1e9))).nextProbablePrime().intValue();
  int[] hash1, hash2, p1, p2;

  public Hash2Mod(CharSequence s) {
    int n = s.length();
    hash1 = new int[n + 1];
    hash2 = new int[n + 1];
    p1 = new int[n + 1];
    p2 = new int[n + 1];
    p1[0] = 1;
    p2[0] = 1;

    for (int i = 0; i < n; i++) {
      hash1[i + 1] = (int) ((hash1[i] * multiplier + s.charAt(i)) % mod1);
      hash2[i + 1] = (int) ((hash2[i] * multiplier + s.charAt(i)) % mod2);
      p1[i + 1] = (int) (p1[i] * multiplier % mod1);
      p2[i + 1] = (int) (p2[i] * multiplier % mod2);
    }
  }

  // 下标从0开始的一段长度
  public long get(int i, int len) {
    long h1 = (hash1[i + len] + (long) hash1[i] * (mod1 - p1[len])) % mod1;
    long h2 = (hash2[i + len] + (long) hash2[i] * (mod2 - p2[len])) % mod2;
    return (h1 << 32) + h2;
  }


}