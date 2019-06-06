package luid;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LUID {
	private int len;
	private int id;
	private AtomicInteger padStart = new AtomicInteger(0);
	
	public LUID(int len) {
		this(len, (new Random()).nextInt(62));
	}
	
	public LUID(int len, int id) {
		this.len = len;
		this.id = id;
	}
	
	public String idBase62(long number) {
		String encode = Base62.encode(id) + Base62.encode(number);
		if (encode.length() < len) {
			encode = encode + pad(len - encode.length()); 
		}
		if (encode.length() > len) {
			encode = encode.substring(encode.length() - len);
		}
		return encode;
	}
	
	private String pad(int len) {
		StringBuilder pad = new StringBuilder(len);
		pad.append(Base62.encode(padStart.getAndIncrement()));
		while (pad.length() < len) {
			pad.insert(0, '0');
		}
		if (pad.length() > len) {
			pad.delete(0, pad.length() - len);
			padStart.set(0);
		}
		return pad.toString();
	}

}
