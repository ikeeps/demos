package javaType;

import java.util.*;

public class TreeSetDemos {

	public static void main(String[] args) {
		TreeSet<Value> set = new TreeSet<>();
		
		set.add(new Value("a", "b"));
		set.add(new Value("a", "b"));
		set.add(new Value("b", "c"));
		
		System.out.println(set.size());
	}

	public static class Value implements Comparable<Value> {
		private String val1;
		private String val2;

		public Value(String val1, String val2) {
			this.val1 = val1;
			this.val2 = val2;
		}

		public String getVal1() {
			return val1;
		}

		public String getVal2() {
			return val2;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((val1 == null) ? 0 : val1.hashCode());
			result = prime * result + ((val2 == null) ? 0 : val2.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Value other = (Value) obj;
			if (val1 == null) {
				if (other.val1 != null)
					return false;
			} else if (!val1.equals(other.val1))
				return false;
			if (val2 == null) {
				if (other.val2 != null)
					return false;
			} else if (!val2.equals(other.val2))
				return false;
			return true;
		}

		@Override
		public int compareTo(Value o) {
			if (this.val1.equals(o.val1)) {
				return this.val2.compareTo(o.val2);
			} else {
				return this.val1.compareTo(o.val1);
			}
		}

	}
}
