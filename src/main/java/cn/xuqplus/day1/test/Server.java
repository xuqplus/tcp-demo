package cn.xuqplus.day1.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Server {
    private static final int port = 11001;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap = bootstrap.group(bossGroup, workerGroup);
            bootstrap = bootstrap.channel(NioServerSocketChannel.class);
            bootstrap = bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) {

                    channel.pipeline().addLast(new HttpRequestDecoder());
                    channel.pipeline().addLast(new HttpResponseEncoder());
                    channel.pipeline().addLast(new ChunkedWriteHandler());
                    channel.pipeline().addLast(new ServerHandler());
                }
            });
            bootstrap = bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap = bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
