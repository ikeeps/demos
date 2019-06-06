package luid;

public class Base62 {

	private static char[] map = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	public static String encode(long number) {
		if (number == 0) {
			return "0";
		}
		StringBuilder encoded = new StringBuilder();
		while(number > 0) {
			encoded.append(map[(int) (number % 62)]);
			number = number / 62;
		}
		return encoded.reverse().toString();
	}
	
	public static long decode(String encoded) {
		long number = 0;
		
		char[] charArray = encoded.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if ('0' <= charArray[i] && charArray[i] <= '9') {
				number = number * 62 + charArray[i] - '0';
			}
			if ('a' <= charArray[i] && charArray[i] <= 'z') {
				number = number * 62 + charArray[i] - 'a' + 10;
			}
			if ('A' <= charArray[i] && charArray[i] <= 'Z') {
				number = number * 62 + charArray[i] - 'A' + 36;
			}
		}
		return number;
	}
}
