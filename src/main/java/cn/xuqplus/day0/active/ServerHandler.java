package cn.xuqplus.day0.active;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;

public class ServerHandler extends ChannelHandlerAdapter {
    private static void log(Object o) {
        System.out.println(o);
    }

    /**
     * 消息处理
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        ByteBuf buf = ctx.alloc().buffer(4);
        buf.writeLong((System.currentTimeMillis()));
        final ChannelFuture future = ctx.writeAndFlush(buf);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                assert future == channelFuture;
                ctx.close();
            }
        });
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
        throwable.printStackTrace();
        ctx.close();
    }
}
