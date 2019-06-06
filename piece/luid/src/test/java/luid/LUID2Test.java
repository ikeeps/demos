package luid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class LUID2Test {

	@Test
	public void id() {
		assertEquals(10, LUID2.idBase62().length());
		assertNotEquals(LUID2.idBase62(), LUID2.idBase62());
		System.out.println(LUID2.idBase62());
		System.out.println(LUID2.idBase62());
	}
	
	@Test
	public void counter() {
		AtomicInteger counter = new AtomicInteger(0);
		
		int total = 62 * 62 * 62;
		final int digit2 = 62 * 62;
		for (int i = 0; i < total; ++i) {
			int num = counter.updateAndGet(x -> x < digit2 - 1 ? x + 1 : 0);
			assertTrue(num < digit2);
		}
	}
}
