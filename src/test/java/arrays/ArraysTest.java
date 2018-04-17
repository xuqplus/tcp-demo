package arrays;

import org.junit.Test;

import java.util.Arrays;

public class ArraysTest {

    @Test
    public void copy() {
        byte[] b = Arrays.copyOf(new byte[]{1, 2, 3, 4, 5}, 19);
        System.out.println(b);
    }
}
