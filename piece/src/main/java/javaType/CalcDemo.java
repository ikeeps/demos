package javaType;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CalcDemo {
  public static void main(String[] args) {
    int imod = 2 << 31;
    System.out.println(imod);
    long lmod = 2l << 31;
    System.out.println(lmod);
    System.out.println(Math.pow(2, 32));
    System.out.println(3 << 2);
    System.out.println(Integer.MAX_VALUE);
    System.out.println(Integer.MIN_VALUE);
    boolean a = false;
    boolean b = false;
    System.out.println(a | b);
        
    System.out.println(Long.toHexString(((-1L) << 24) >>> 8));
    System.out.println(106 + 15 << 2);

    long total = 2147483649L;
    System.out.println(total & Integer.MAX_VALUE);
    System.out.println(TimeUnit.MILLISECONDS.toDays(Integer.MAX_VALUE));
    System.out.println(TimeUnit.MICROSECONDS);
    long STREGTHEN_TIME = 300000;
    for(long remain = 3 * STREGTHEN_TIME; remain > 899990; remain--) {
      long moneyNeed = (remain / STREGTHEN_TIME + 
          (remain % STREGTHEN_TIME == 0 ? 0 : 1 )) * 1;
      System.out.println(moneyNeed);
    }

    testbyteToUnsign();
    
    for (int i = 0; i < 100; i+=5) {
      for (int j = 0; j < 10; ++j) {
        System.out.println(i + " : " + j + " : " + getValue(i, j));
      }
    }
  }

  public static boolean adjacent(int i, int j) {
    return (Math.abs((i >> 2) - (j >> 2)) == 1 && Math.abs((i & 3) - (j & 3)) == 0)
        || (Math.abs((i >> 2) - (j >> 2)) == 0 && Math.abs((i & 3) - (j & 3)) == 1);
  }
  
  public static void testAdjacent() {
    Random rand = new Random();
    int a;
    int b;
    for (int i = 0; i < 16; i++) {
      a = rand.nextInt(16);
      b = rand.nextInt(16);
      System.out.printf("%d %d : %b\n", a, b, adjacent(a, b));

    }
    adjacent(5, 10);
  }
  
  public static int byteToUnsign(byte a) {
    return a & 0xFF;
  }
  
  public static void testbyteToUnsign() {
    byte a = 127;
    int b = a & 0xff;
    // 上线为128，会导致无限循环
    for(byte i=-128; i<127; i++) {
      System.out.println(byteToUnsign(i));
    }
    System.out.println(b);
  }
  
  public static int getValue(int level, int count) {
    return (int) (Math.floor((level * 100 + (float)((level * 100 + 2000) * 10 * count) / 45 + 18050) / 100) * 100);
  }
  

}
