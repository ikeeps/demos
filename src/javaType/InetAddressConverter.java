package javaType;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class InetAddressConverter {
  public static void main(String[] args) throws IOException {
    
    InetAddress add = InetAddress.getByName("192.168.0.34");
    byte[] bAddr = add.getAddress();
    ByteBuffer buffer = ByteBuffer.wrap(bAddr);
    System.out.println(buffer.order());
    buffer.order(ByteOrder.LITTLE_ENDIAN);
    int iAddr = buffer.getInt(); 

    for (int i = 0; i < 4; ++i) {
      System.out.println(Integer.toHexString(bAddr[i] & 0xff));
    }
    long lAddr = iAddr & 0xFFFFFFFFl;
    
    System.out.println(Integer.toHexString(iAddr) + "\t" + Long.toHexString(lAddr));
    System.out.println(add.getHostAddress());
    System.out.println(add.getHostName());
    

  }
}
