package jdkUse.Internet.javaSocket.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(1099);
        SocketChannel socketChannel = SocketChannel.open(address);

        System.out.println("Connecting to Server on port " + address.getAddress().getHostAddress() + ".....");

        ArrayList<String> companyDetails = new ArrayList<>();

        // create a ArrayList with companyName list
        companyDetails.add("Facebook");
        companyDetails.add("abc");

        companyDetails.forEach(x -> {
            ByteBuffer buffer = ByteBuffer.wrap(x.getBytes());
            try {
                socketChannel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("send ..... " + x);

            buffer.clear();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        socketChannel.close();
    }
}
