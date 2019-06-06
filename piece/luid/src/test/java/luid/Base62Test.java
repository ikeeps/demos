package luid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Base62Test {

	@Test
	public void encode() {
		assertEquals("0", Base62.encode(0));
		assertEquals("1", Base62.encode(1));
		assertEquals("10", Base62.encode(62));
		assertEquals("43u", Base62.encode(4 * 62 * 62 + 3 * 62 + 30));
		
	}
	
	@Test
	public void decode() {
		assertEquals(0, Base62.decode("0"));
		assertEquals(1, Base62.decode("1"));
		assertEquals(62, Base62.decode("10"));
		assertEquals(4 * 62 * 62 + 3 * 62 + 30, Base62.decode("43u"));
	}
}
