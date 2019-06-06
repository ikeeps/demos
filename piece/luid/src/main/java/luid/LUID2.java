package luid;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * IP last byte + second + random.nextInt(62) + counter
 * 
 * @author ikeeps
 *
 */
public class LUID2 {
	private static int id = -1;
	private static AtomicInteger counter = new AtomicInteger(0);
	private static Random rand = new Random();
	
	static {
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			if (!localHost.isLoopbackAddress()) {
				byte b = localHost.getAddress()[3];
				if (b == 0 || b == 1) {
					id = rand.nextInt(62);
				} else {
					id = b % 62;
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if (id == -1) {
			id = rand.nextInt(62);
		}
	}
	
	public static String idBase62() {
		String encode = Base62.encode(id) + Base62.encode(System.currentTimeMillis() / 1000) + Base62.encode(rand.nextInt(62));
		
		return encode + pad(10 - encode.length());
	}
	
	private static String pad(int len) {
		StringBuilder pad = new StringBuilder(len);
		pad.append(Base62.encode(counter.getAndIncrement()));
		while (pad.length() < len) {
			pad.insert(0, '0');
		}
		if (pad.length() > len) {
			pad.delete(0, pad.length() - len);
			counter.set(0);
		}
		return pad.toString();
	}

}
