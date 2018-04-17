package cn.xuqplus.socket.day1;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class BindSocket {

    static int port = 11001;

    public static void main(String[] args) {
        server();
        client();
    }

    static void client() {
        new Thread(() -> {
            try {
                try (Socket socket = new Socket(InetAddress.getLocalHost(), port)) {
                    try (OutputStream os = socket.getOutputStream()) {
                        while (!Thread.interrupted()) {
                            os.write(new byte[]{1, 2, 3, 4, 5, 6, 7});
                            Thread.sleep(100);
                            os.write("你好, 世界. 123abc".getBytes());
                            Thread.sleep(100);
                            os.write("面".getBytes());
                            Thread.sleep(100);
                            os.write("只".getBytes());
                            Thread.sleep(100);
                            os.write("只只".getBytes());
                            Thread.sleep(100);
                            Thread.sleep(1000);
                        }
                    }
                }
            } catch (Exception e) {

            }
        }).start();
    }

    static void server() {
        new Thread(() -> {
            try {
                try (ServerSocket serverSocket = new ServerSocket()) {
                    serverSocket.bind(new InetSocketAddress(port));
                    serverSocket.setSoTimeout(1000 * 20);
                    while (!Thread.interrupted()) {
                        try (Socket socket = serverSocket.accept()) {
                            System.out.println("============== done accept 1 ==============");
                            InputStream is = socket.getInputStream();
                            OutputStream os = socket.getOutputStream();
                            byte[] buf = new byte[2048];
                            int i;
                            while (-1 != (i = is.read(buf))) {
                                os.write((i + "bytes, ").getBytes("utf8"));
                                os.write(Arrays.copyOf(buf, i));
                                System.out.println(i + "bytes, " + Arrays.copyOf(buf, i));
                                System.out.println(i + "bytes, " + new String(Arrays.copyOf(buf, i)));
                            }
                            System.out.println("============== done accept 2 ==============");
                        }
                        System.out.println("============== done once ==============");
                    }
                }
            } catch (Exception e) {

            }
        }).start();
    }
}
