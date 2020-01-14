package luid;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * IP last byte % 62 + nanosecond % 62 + second + counter
 * 
 * at most 1 second per unique (ip + nanosecond) => 62 * 62 ID
 * 
 * @author ikeeps
 *
 */
public class LUID2 {
	private static ThreadLocal<Integer> id = new ThreadLocal<>();
	private static AtomicInteger counter = new AtomicInteger(0);
	private static final int DIGIT2 = 62 * 62 - 1;
	
	private static int nodeID() {
		Integer re = id.get();
		if (re == null) {
			return setNodeID();
		} else {
			return re;
		}
	}
	
	private static int setNodeID() {
		int id1 = -1;
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			if (!localHost.isLoopbackAddress()) {
				id1 = localHost.getAddress()[3] % 62;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if (id1 == -1) {
			id1 = ThreadLocalRandom.current().nextInt(62);
		}
		int calID = (int)(id1 * 62 + System.nanoTime() % 62);
		id.set(calID);
		return calID;
	}
	
	public static String idBase62() {
		String encode = Base62.encode(nodeID()); 
		
		return encode + pad(10 - encode.length());
	}
	
	private static String pad(int len) {
		StringBuilder pad = new StringBuilder(len);
		pad.append(Base62.encode(System.currentTimeMillis() / 1000));
		String count = Base62.encode(counter.updateAndGet(operand -> operand < DIGIT2 ? operand + 1 : 0));
		pad.append(count);
		while (pad.length() < len) {
			pad.insert(pad.length() - count.length(), '0');
		}
		if (pad.length() > len) {
			pad.delete(0, pad.length() - len);
		}
		return pad.toString();
	}

}
