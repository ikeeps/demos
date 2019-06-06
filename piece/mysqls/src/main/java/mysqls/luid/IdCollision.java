package mysqls.luid;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import luid.IdGeneratorTask2;

public class IdCollision {

	public static void main(String[] args) {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 4; i ++) {
			newFixedThreadPool.execute(new IdGeneratorTask2(i));
		}
	}
}
