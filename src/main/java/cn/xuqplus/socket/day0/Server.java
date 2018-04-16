package cn.xuqplus.socket.day0;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        String msg = "hello client, I am reply..";
        InetAddress address = InetAddress.getLocalHost();
        //构造ServerSocket实例，指定端口监听客户端的连接请求
        try (ServerSocket serverSocket = new ServerSocket(11001)) {
            //建立跟客户端的连接
            try (Socket socket = serverSocket.accept()) {
                //向客户端发送消息
                OutputStream os = socket.getOutputStream();
                os.write(msg.getBytes());
                InputStream is = socket.getInputStream();
                //接受客户端的响应
                byte[] b = new byte[1024];
                while (true) {
                    is.read(b);
                    System.out.println(new String(b));
                }
            }
        }
    }
}
