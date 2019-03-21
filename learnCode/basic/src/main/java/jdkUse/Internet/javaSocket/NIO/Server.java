package jdkUse.Internet.javaSocket.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress( 1099);

        serverSocketChannel.bind(inetSocketAddress);

        serverSocketChannel.configureBlocking(false);

        int ops = serverSocketChannel.validOps();

        SelectionKey selectionKey = serverSocketChannel.register(selector, ops, null);

        while (true) {
            System.out.println("i'm a server and i'm waiting for new connection and buffer select...");

            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {
                    SocketChannel channel = serverSocketChannel.accept();

                    channel.configureBlocking(false);

                    channel.register(selector, SelectionKey.OP_READ);

                    System.out.println("Connection Accepted: " + channel.getLocalAddress() + "\n");
                } else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();

                    ByteBuffer buffer = ByteBuffer.allocate(256);

                    channel.read(buffer);

                    String result = new String(buffer.array()).trim();

                    System.out.println("Message received: " + result);

                    if (result.equals("abc")) {
                        channel.close();
                        System.out.println("It's time to close connection as we got last company name 'abc'");
                        System.out.println("Server will keep running. Try running client again to establish new connection\n");
                    }
                }
                iterator.remove();
            }
        }
    }
}
