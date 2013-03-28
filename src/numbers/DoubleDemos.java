package numbers;

/**
 * 
 * @author user
 *
 * output 
 * 99.0
 * 98.99999618530273
 * 99.0
 * 99.0
 * 1939.9999999999998
 */
public class DoubleDemos {
  public static void main(String[] args) {
    double d = 9.9;
    System.out.println(d * 10);
    d = 9.9f;
    System.out.println(d * 10);
    float  f = 9.9f;
    System.out.println(f * 10);
    f = (float) d;
    System.out.println(f * 10);
    double d1 = 38.8;
    System.out.println(d1 * 50);
  }
}
