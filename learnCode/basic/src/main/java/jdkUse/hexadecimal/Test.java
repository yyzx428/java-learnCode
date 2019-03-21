package jdkUse.hexadecimal;

public class Test {
    public static void main(String[] args) {

        Integer a = (1 << 6) - 1;

        //2进制
        System.out.println(Integer.toBinaryString(a));

        //16进制
        System.out.println(Integer.toHexString(a));
    }
}
