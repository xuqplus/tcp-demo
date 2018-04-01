package cn.xuqplus.day0.pojo;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {
    private static void log(Object o) {
        System.out.println(o);
    }

    /**
     * 消息处理
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        User user = new User();
        user.id = 7L;
        user.name = "世界上最帅的人";
        ctx.writeAndFlush(user);
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
