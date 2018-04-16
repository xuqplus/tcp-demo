package cn.xuqplus.socket.day0;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class BindChannel {
    public static void main(String[] args) throws IOException {
        try (ServerSocketChannel channel = ServerSocketChannel.open()) {
            channel.bind(new InetSocketAddress(11001));
            while (!Thread.interrupted()) {
                try (SocketChannel c = channel.accept()) {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    while (-1 != c.read(buffer)) {
                        int i = c.read(buffer);
                        System.out.println(i + "bytes, " + new String(buffer.array()));
                    }
                    System.out.println("============== done ==============");
                }
            }
        }
    }
}
