package jdkUse.UnSafe;

import sun.misc.Unsafe;

public class UnSafeTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        System.out.println(unsafe.getChar(12));
    }
}
