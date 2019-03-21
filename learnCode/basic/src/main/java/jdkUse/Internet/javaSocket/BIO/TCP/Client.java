package jdkUse.Internet.javaSocket.BIO.TCP;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        boolean writerSplit = false;
        String host = "localhost";

        Socket socket = new Socket();

        socket.connect(new InetSocketAddress(host, 1099));

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String head = "head";
        String body = "body" +System.lineSeparator();

        for (int i = 0; i < 10; i++) {
            long label = System.currentTimeMillis();
            if (writerSplit) {
                out.write(head.getBytes());
                out.write(body.getBytes());
            } else {
                out.write((head + body).getBytes());
            }

            String line = reader.readLine();
            System.out.println("RTT:" + (System.currentTimeMillis() - label) + " receive:" + line);
        }

        in.close();
        out.close();
        socket.close();
    }
}
