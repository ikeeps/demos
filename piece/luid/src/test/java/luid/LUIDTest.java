package luid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LUIDTest {

	@Test
	public void id() {
		LUID gen1 = new LUID(7);
		LUID gen2 = new LUID(6);
		LUID gen3 = new LUID(10);
		long time = System.currentTimeMillis();
		String idTime = gen1.idBase62(time);
		String idPartTime = gen2.idBase62(time);
		String idTimeRandom = gen3.idBase62(time);
		assertEquals(7, idTime.length());
		assertEquals(6, idPartTime.length());
		assertTrue(idTime.contains(idPartTime));
		assertEquals(10, idTimeRandom.length());
		assertTrue(idTimeRandom.contains(idTime));
		
		System.out.println(idTime);
		System.out.println(idPartTime);
		System.out.println(idTimeRandom);
	}

	
	@Test
	public void idCollision() {
		
	}
	
	
}
