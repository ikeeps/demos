package javaType;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class LambdaExample {

	public static void main(String[] args) {
		Op op = (a, b) -> a + b;
		String call = op.call("hello ", "world");
		System.out.println(call);
		
		LambdaExample ex = new LambdaExample();
		TreeSet<String> map = Arrays.asList("a", "b", "c").stream().map(ex::contact).collect(Collectors.toCollection(TreeSet::new));
		System.out.println(map);
	}

	public String contact(String a) {
		return a + " append";
	}
	
	public String call(Op op) {
		return op.call("hello", "world");
	}
}
