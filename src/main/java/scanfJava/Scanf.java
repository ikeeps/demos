package scanfJava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Scanf {
    public static void main(String[] args) throws IOException {
        Pattern pa = Pattern.compile("\\S*"); // %s
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = reader.readLine();
        Scanner scanner = new Scanner(s);
        String word = scanner.next(pa); // get %s
        scanner.skip(" : "); // origin
        int i = scanner.nextInt(); // %d
        System.out.println(word + " : " + i);
    }
}
