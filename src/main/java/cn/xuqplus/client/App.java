package cn.xuqplus.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try {
            //对服务端发起连接请求
            socket = new Socket("localhost", 8080);
            //接受服务端消息并打印
            InputStream is = socket.getInputStream();
            byte b[] = new byte[1024];
            is.read(b);
            System.out.println(new String(b));
            //给服务端发送响应信息
            OutputStream os = socket.getOutputStream();
            os.write("yes,I have received you message!".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != socket) {
                socket.close();
            }
        }
    }
}
