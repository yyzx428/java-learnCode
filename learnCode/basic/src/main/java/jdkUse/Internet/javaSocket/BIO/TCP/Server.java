package jdkUse.Internet.javaSocket.BIO.TCP;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost", 1099));
        System.out.println("Server start at 1099");
        for (; ; ) {
            Socket socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            while (true) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = reader.readLine();
                    out.write((line + System.lineSeparator()).getBytes());
                } catch (Exception e) {
                    break;
                }
            }
        }
    }
}
