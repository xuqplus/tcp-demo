package cn.xuqplus.day0.active;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
    private static void log(Object o) {
        System.out.println(o);
    }

    private static void log(String format, Object... value) {
        System.out.println(String.format(format, value));
    }

    /**
     * 开始
     */
    public static void main(String[] args) throws InterruptedException {
        String host = "localhost";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) {
                    channel.pipeline().addLast(new ClientHandler());
                }
            });
            //启动客户端
            ChannelFuture future = bootstrap.connect(host, port);
            //等待连接关闭
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
        log("开始");
    }
}
