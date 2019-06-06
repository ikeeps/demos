package algorithms;

public class Fib {
  public static void main(String[] args) {
    for (int i = 1; i < 200; i+= 2) {
      long now = System.currentTimeMillis();
      System.out.println(i + " : " + fib1(i) + " " + format(System.currentTimeMillis() , now));
    }
  }
  
  public static int fib1(int n) {
    if (n == 0) return 0;
    if (n == 1) return 1;
    int re = fib1(n - 1) + fib1(n - 2);
    System.out.println(n + "----" + re);
    return re;
  }
  
  public static double fib2(int n) {
    double[] result = new double[n + 1];
    result[0] = 0;
    result[1] = 1;
    for (int i = 2; i < result.length; i++) {
      result[i] = result[i - 1] + result[i - 2];
    }
    return result[n];
  }
  
  static int one_hour = 60 * 60 * 1000;
  static int one_minute = 60 * 1000;
  static int one_second = 1000;
  public static String format(long now, long old) {
    long delta = now - old;
    int hour = (int) delta / one_hour;
    delta -= hour * one_hour;
    int minutes = (int) delta / one_minute;
    delta -= minutes * one_minute;
    int s = (int) delta / one_second;
    delta -= s * one_second;
    return "h" + hour + " m" + minutes + " s" + s + " ms" + delta;
  }
}
