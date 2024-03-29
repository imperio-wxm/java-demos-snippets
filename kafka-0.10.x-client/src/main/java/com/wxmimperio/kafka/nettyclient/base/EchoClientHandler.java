package com.wxmimperio.kafka.nettyclient.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by weiximing.imperio on 2017/1/4.
 */

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private Map<String,Long> topicPartitionCount;

    public EchoClientHandler(Map<String,Long> topicPartitionCount) {
        this.topicPartitionCount = topicPartitionCount;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client channelActive..");
        System.out.println("Thread=" + Thread.currentThread() + " " + this.topicPartitionCount);
        ctx.writeAndFlush(this.topicPartitionCount); // 必须有flush
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
