package cn.xuqplus.day1.reply;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext context, Object o) {
        ByteBuf buf = (ByteBuf) o;
        System.err.println(buf.toString(CharsetUtil.UTF_8));
        context.writeAndFlush(buf);
    }
}
