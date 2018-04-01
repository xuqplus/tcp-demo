package cn.xuqplus.day0.pojo;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelHandlerAdapter {
    private static void log(Object o) {
        System.out.println(o);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            User user = (User) msg;
            log(user.toString());
        } finally {
            ctx.close();
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
        throwable.printStackTrace();
        ctx.close();
    }
}
