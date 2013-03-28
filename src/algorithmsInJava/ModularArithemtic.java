package algorithmsInJava;


public class ModularArithemtic {

  /**
   * 
   * @param a
   * @param b
   * @return
   */
  public static long exponentiation(long a, int b) {

    if (b == 0) return 1;
    if (b == 1) return a;

    long z = exponentiation(a, b >> 1);
    if ((b & 1) == 0) {
      z = z * z;
    } else {
      z = z * z * a;
    }
    return z;
  }
  
  public static long exponentiation1(long a, int b, long mod) {
    if (b == 0) return 1;
    if (b == 1) return a;
    long[] result = new long[b + 1];
    result[0] = 1;
    result[1] = a;
    int tmp;
    for (int i = 2; i < result.length; ++i) {
      tmp = i / 2;
      result[i] = result[tmp] * result[tmp];
      if ((i & 1) == 1) {
        result[i] *= a;
      }
      
    }
    return result[b];
  }
  
  public static void main(String[] args) {
    for (int i = 0; i < 64; i ++) {
      System.out.println(exponentiation1(2, i, i));
    }
  }
  
}
