package cn.xuqplus.day0.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class DiscardServer {
    private static void log(Object o) {
        System.out.println(o);
    }

    private static void log(String format, Object... value) {
        System.out.println(String.format(format, value));
    }

    private int port;

    public DiscardServer(int port) {
        super();
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        log("start with port=%s", port);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap = bootstrap.group(bossGroup, workerGroup);
            bootstrap = bootstrap.channel(NioServerSocketChannel.class);
            bootstrap = bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) {
                    channel.pipeline().addLast(new DiscardServerHandler());
                }
            });
            bootstrap = bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            bootstrap = bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    /**
     * 开始
     */
    public static void main(String[] args) throws InterruptedException {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new DiscardServer(port).run();
        log("开始");
    }
}
