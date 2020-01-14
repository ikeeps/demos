package luid;

public class Statistics {

	private long lastPrint = System.currentTimeMillis();
	private int count = 0;
	private int total = 0;
	private String name;
	
	public Statistics(String name) {
		this.name = name;
	}
	
	public void incr() {
		int oldCount = count++;
		total++;
		long curExecute = System.currentTimeMillis();
		long delta = curExecute - lastPrint;
		if (delta > 600000) {
			System.out.println("name:" + name + " delta:" + delta + " count:" + oldCount + " total:" + total);
			lastPrint = curExecute;
			count = 0;
		}
	}
}
