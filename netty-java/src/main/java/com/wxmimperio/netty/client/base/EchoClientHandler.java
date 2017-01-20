package com.wxmimperio.netty.client.base;

import java.nio.charset.Charset;

import com.wxmimperio.netty.pojo.TopicCount;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by weiximing.imperio on 2017/1/4.
 */

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private TopicCount topicCount;

    public EchoClientHandler(TopicCount topicCount) {
        this.topicCount = topicCount;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client channelActive..");
        System.out.println(this.topicCount);
        ctx.writeAndFlush(this.topicCount); // 必须有flush
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("client channelRead..");
        ByteBuf buf = msg.readBytes(msg.readableBytes());
        System.out.println("Client received:" + ByteBufUtil.hexDump(buf) + "; The value is:" + buf.toString(Charset.forName("utf-8")));
        //ctx.channel().close().sync();// client关闭channel连接
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
