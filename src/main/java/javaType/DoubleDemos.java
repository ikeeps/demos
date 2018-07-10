package javaType;

/**
 * 
 * @author user
 *
 */
public class DoubleDemos {
  public static void main(String[] args) {
    double d = 9.9;
    System.out.println(d * 10); // 99.0
    d = 9.9f;
    System.out.println(d * 10); // 98.99999618530273
    float  f = 9.9f;
    System.out.println(f * 10); // 99.0
    f = (float) d;
    System.out.println(f * 10); // 99.0
    double d1 = 38.8;
    System.out.println(d1 * 50); // 1939.9999999999998
  }
}
