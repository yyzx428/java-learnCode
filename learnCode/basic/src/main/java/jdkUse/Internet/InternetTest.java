package jdkUse.Internet;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class InternetTest {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress[] allByName = InetAddress.getAllByName("www.baidu.com");
        Arrays.asList(allByName).forEach(x -> {
            System.out.println(x.getHostAddress());
        });
    }
}
