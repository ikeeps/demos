package javaType;

public class IntegerCompare {
	public static void main(String[] args) {
		int i2 = 0;
		for (int i = 0; i < 10; i++) {
			Integer integer = new Integer(i);
			System.out.println(integer);
			System.out.println(i2);
			
			System.out.println("-----------");
			if (integer != i2) {
				System.out.println(i);
			}
			i2++;
		}
		
		Integer a = 333;
		Integer b = 333;
		
		System.out.println(a == b);
		
		Boolean test = null;

		if (Boolean.FALSE.equals(test)) {
			System.out.println("err....");
		} else {
			System.out.println("else err....");
		}

		
		if (test) {
			System.out.println("err....");
		} else {
			System.out.println("else err....");
		}
		
	}
}
