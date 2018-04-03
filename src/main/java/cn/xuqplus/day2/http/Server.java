package cn.xuqplus.day2.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Server {

    private static final int port = 11001;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap = bootstrap.group(bossGroup, workerGroup);
            bootstrap = bootstrap.channel(NioServerSocketChannel.class);
            bootstrap = bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) {

                    channel.pipeline().addLast(new HttpRequestDecoder());
                    channel.pipeline().addLast(new HttpResponseEncoder());
                    // channel.pipeline().addLast(new HttpObjectAggregator(1 << 16));
                    channel.pipeline().addLast(new ChunkedWriteHandler());
                    channel.pipeline().addLast(new ServerHandler());
                }
            });
            bootstrap = bootstrap.option(ChannelOption.SO_BACKLOG, 1022);
            bootstrap = bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println(String.format("server started with port=%s", port));
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
