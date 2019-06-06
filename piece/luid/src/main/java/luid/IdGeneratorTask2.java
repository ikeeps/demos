package luid;
import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;

public class IdGeneratorTask2 implements Runnable {

	private static AtomicLong count = new AtomicLong(0);
	private int num;
	
	public IdGeneratorTask2(int num) {
		this.num = num;
	}
	
	@Override
	public void run() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			throw new IllegalStateException(e1);
		}
		Statistics statistics = new Statistics(Integer.toString(num));
		final int total = 100 * 10000;
		try(Connection con = DriverManager.getConnection("jdbc:mysql://192.168.99.31:3306/demo", "sammy", "12345678");
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO demo.base62id (id, create_at, num, thread) VALUES(?, ?, ?, ?)");) {
			int cur = 0;
			while (cur < total) {
				long currentTimeMillis = System.currentTimeMillis();
				String id = LUID2.idBase62();
				pstmt.setString(1, id);
				pstmt.setTimestamp(2, new Timestamp(currentTimeMillis));
				pstmt.setInt(3, cur);
				pstmt.setInt(4, num);
				try {
					int executeUpdate = pstmt.executeUpdate();
					if (executeUpdate == 1) {
						count.incrementAndGet();
						statistics.incr();
					} else {
						System.out.println("thread:" + num + " total:" + count.get() + " id:" + id + " update:" + executeUpdate);
					}
				} catch (SQLException e1) {
					System.out.println("thread:" + num + " total:" + count.get() + " id:" + id + " error:" + e1);
				}
				cur++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("thread " + num + " create finish");
	}
	
}
