package cn.xuqplus.socket.day0;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BindSocket {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(11110));
        serverSocket.setSoTimeout(1000 * 10);
        try (Socket socket = serverSocket.accept()) {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            byte[] buf = new byte[2048];
            int i = is.read(buf);
            os.write(buf);
            System.out.println(i + "bytes, " + new String(buf));
        }
    }
}
