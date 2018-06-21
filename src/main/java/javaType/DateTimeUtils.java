package javaType;

import java.util.Calendar;

public class DateTimeUtils {
  /**
   * 
   * @param since
   * @return 8 time point from the previous midnight of since to the 7th day's midnight  
   * 
   */
  public static long[] getMidNightPoints(long since, int days) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(since);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    // 小于idx的time，就是idx天的登录
    long[] time_points = new long[days + 1];
    time_points[0] = calendar.getTimeInMillis();
    for (int i = 1; i < time_points.length; i++) {
      calendar.add(Calendar.DAY_OF_MONTH, 1);
      time_points[i] = calendar.getTimeInMillis();
    }
    return time_points;
  }
  
  public static long getDayAfter(long now, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(now);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.add(Calendar.DAY_OF_MONTH, day);
    return calendar.getTimeInMillis();
  }
}
