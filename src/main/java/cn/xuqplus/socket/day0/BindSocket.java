package cn.xuqplus.socket.day0;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class BindSocket {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(11001));
        serverSocket.setSoTimeout(1000 * 20);
        while (!Thread.interrupted()) {
            try (Socket socket = serverSocket.accept()) {
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                byte[] buf = new byte[2048];
                int i;
                while (-1 != (i = is.read(buf))) {
                    os.write((i + "bytes, ").getBytes("utf8"));
                    os.write(Arrays.copyOf(buf, i));
                    System.out.print(i + "bytes, " + new String(Arrays.copyOf(buf, i)));
                }
                System.out.println("============== done ==============");
            }
        }
    }
}
