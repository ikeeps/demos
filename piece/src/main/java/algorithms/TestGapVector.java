package algorithms;

import java.util.Arrays;
/**
 * 
 * TestGapVector
 * 
 * @author hujianfeng
 * @date 2018/06/21
 *
 */
@SuppressWarnings("serial")
public class TestGapVector extends GapVector {
    @Override
    protected Object allocateArray(int len) {
        return new char[len];
    }

    @Override
    protected int getArrayLength() {
        return ((char[]) getArray()).length;
    }

    public String toString() {
        return Arrays.toString(((char[]) getArray())) + " : " + getGapStart() + " : " + getGapEnd();
    }

    public static void main(String[] args) {
        System.out.println("abcdef".toCharArray().length);
        TestGapVector gap = new TestGapVector();
        System.out.println(gap.toString());
        char[] str = new char[] { 'a', 'b', 'c', 'd', 'e', 'f' };
        gap.replace(0, 0, str, str.length);
        char[] is_str = new char[] { 'l', 'm', 'n' };
        gap.replace(3, 0, is_str, is_str.length);
        System.out.println(gap.toString());
        gap.replace(8, 1, new char[0], 0);
        System.out.println(gap.toString());
    }
}
