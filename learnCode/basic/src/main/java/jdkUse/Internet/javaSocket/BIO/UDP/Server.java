package jdkUse.Internet.javaSocket.BIO.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket server = new DatagramSocket(5060);
        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

        server.receive(packet);

        System.out.println(packet.getAddress().getHostName() + "(" + packet.getPort() + "):" + new String(packet.getData()));

        packet.setData("Hello Client".getBytes());
        packet.setPort(5070);
        packet.setAddress(InetAddress.getLocalHost());

        server.send(packet);
        server.close();
    }
}
