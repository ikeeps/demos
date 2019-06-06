package luid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class LUID2Test {

	@Test
	public void id() {
		assertEquals(10, LUID2.idBase62().length());
		assertNotEquals(LUID2.idBase62(), LUID2.idBase62());
		System.out.println(LUID2.idBase62());
		System.out.println(LUID2.idBase62());
	}
}
