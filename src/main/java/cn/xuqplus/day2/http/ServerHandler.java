package cn.xuqplus.day2.http;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.concurrent.Future;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext context, Object o) throws IOException {

        if (o instanceof HttpRequest) {
            DefaultHttpRequest request = (DefaultHttpRequest) o;
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            if (HttpHeaderUtil.isKeepAlive(request)) {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            RandomAccessFile file = new RandomAccessFile(new File("aaa.html"), "r");
            Future future = context.writeAndFlush(new ChunkedFile(file, 0, file.length(), 1 << 13), context.newProgressivePromise());
            context.writeAndFlush(response);
            future.addListener(new ChannelProgressiveFutureListener() {
                public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                    System.out.println(String.format("%d of %d", progress, total));
                }

                public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                    System.err.println(String.format("done at %s", System.currentTimeMillis()));
                }
            });
            ChannelFuture lastContentFuture = context.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
