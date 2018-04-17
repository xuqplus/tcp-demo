package cn.xuqplus.socket.day1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class UdpSocket {

    static int port = 11001;

    public static void main(String[] args) {
        server();
        client();
    }

    static void client() {
        new Thread(() -> {
            try {
                try (DatagramSocket socket = new DatagramSocket()) {
                    socket.connect(InetAddress.getLocalHost(), port);
                    while (!Thread.interrupted()) {
                        byte[] b = new byte[]{1, 2, 3, 4, 5, 6, 7};
                        DatagramPacket packet = new DatagramPacket(b, b.length);
                        socket.send(packet);
                        Thread.sleep(100);

                        b = "你好, 世界. 123abc".getBytes();
                        packet = new DatagramPacket(b, b.length);
                        socket.send(packet);
                        Thread.sleep(100);

                        b = "你好".getBytes();
                        packet = new DatagramPacket(b, b.length);
                        socket.send(packet);
                        Thread.sleep(100);

                        b = "只".getBytes();
                        packet = new DatagramPacket(b, b.length);
                        socket.send(packet);
                        Thread.sleep(100);

                        Thread.sleep(1000);
                    }
                }
            } catch (Exception e) {

            }
        }).start();
    }

    static void server() {
        new Thread(() -> {
            try {
                try (DatagramSocket serverSocket = new DatagramSocket(port, InetAddress.getLocalHost())) {
                    while (!Thread.interrupted()) {
                        byte[] buf = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(buf, buf.length);
                        serverSocket.receive(packet);
                        System.out.println(new String(Arrays.copyOf(packet.getData(), packet.getLength())));
                    }
                }
            } catch (Exception e) {

            }
        }).start();
    }
}
