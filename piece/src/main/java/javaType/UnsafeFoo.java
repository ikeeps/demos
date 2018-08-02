package javaType;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

//import sun.misc.Unsafe;
//
//public class UnsafeFoo {
//  public static void main(String[] args) throws PrivilegedActionException {
//    // 通过Unsafe的private字段取得实例。
//    Unsafe theUnsafe = AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() {
//
//      public Unsafe run() throws Exception {
//        Field unsafe = Unsafe.class.getDeclaredField("theUnsafe");
//        unsafe.setAccessible(true);
//        return (Unsafe) unsafe.get(null);
//      }
//      
//    });
//    System.out.println(theUnsafe == null);
//  }
//}
