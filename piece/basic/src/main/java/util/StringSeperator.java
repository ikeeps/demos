/**
 * 
 */
package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hujianfeng
 *
 */
public class StringSeperator {

	public static void main(String[] args) throws IOException {
		try (FileOutputStream fileOutputStream = new FileOutputStream("/test1.txt")) {
			fileOutputStream.write("a,d\r\nb,e\r\nc,f".getBytes());	
		}

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/test1.txt")))) {
			bufferedReader.lines().forEach(str -> System.out.println(Arrays.toString(str.getBytes())));
		}

		try (FileOutputStream fileOutputStream = new FileOutputStream("/test2.txt")) {
			fileOutputStream.write("a,d\nb,e\nc,f".getBytes());	
		}

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/test2.txt")))) {
			List<String> collect = bufferedReader.lines().collect(Collectors.toList());
			
		}
	}
}
