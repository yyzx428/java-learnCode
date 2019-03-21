package redis.protocol;

import redis.clients.jedis.Protocol;
import redis.clients.util.RedisInputStream;
import redis.clients.util.RedisOutputStream;
import redis.clients.util.SafeEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Test {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.setReuseAddress(true);
        socket.setKeepAlive(true);
        socket.setTcpNoDelay(true);
        socket.setSoLinger(true, 0);
        socket.connect(new InetSocketAddress("127.0.0.1", 6379), 10000);
        socket.setSoTimeout(10000);

        OutputStream os = socket.getOutputStream();
        RedisOutputStream redisOutputStream = new RedisOutputStream(os);
        String key = "custom";
        String value = "name:value";
        Protocol.sendCommand(redisOutputStream, Protocol.Command.SET, SafeEncoder.encode(key), SafeEncoder.encode(value));
        redisOutputStream.flush();

        InputStream inputStream = socket.getInputStream();
        RedisInputStream redisInputStream = new RedisInputStream(inputStream);
        Object object = Protocol.read(redisInputStream);
        System.out.println(new String((byte[])object,"UTF-8"));
    }
}
