package cn.xuqplus.day0.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class ClientHandler extends ChannelHandlerAdapter {
    private static void log(Object o) {
        System.out.println(o);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        try {
            long mills = buf.readLong();
            log(mills);
            long mills1 = buf.readLong();
            log(mills1);
            String string = buf.readBytes(6).toString(CharsetUtil.UTF_8);
            log(string);
            ctx.close();
        } finally {
            buf.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
        throwable.printStackTrace();
        ctx.close();
    }
}
