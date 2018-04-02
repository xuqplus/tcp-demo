package cn.xuqplus.day1.test;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.AsciiString;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class ServerHandler extends ChannelHandlerAdapter {
    private static final byte[] CONTENT = "hello world, 你好世界!".getBytes();

    private static final AsciiString CONTENT_TYPE = AsciiString.of("Content-Type");
    private static final AsciiString CONTENT_LENGTH = AsciiString.of("Content-Length");
    private static final AsciiString CONNECTION = AsciiString.of("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.of("keep-alive");

    @Override
    public void channelRead(ChannelHandlerContext context, Object o) {
        if (o instanceof DefaultHttpRequest) {
            DefaultHttpRequest request = (DefaultHttpRequest) o;
            System.err.println(request);
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(CONTENT));
            response.headers().set(CONTENT_TYPE, "text/html; charset=utf-8");
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
            context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
