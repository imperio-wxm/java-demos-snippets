package com.wxmimperio.netty.client.base;

import com.wxmimperio.netty.pojo.TopicCount;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;


/**
 * Created by weiximing.imperio on 2017/1/4.
 */

public class EchoClient implements Runnable {
    private final String host;
    private final int port;
    private TopicCount topicCount;

    public EchoClient(String host, int port, TopicCount topicCount) {
        super();
        this.host = host;
        this.port = port;
        this.topicCount = topicCount;
    }

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group) // 注册线程池
                    .channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
                    .remoteAddress(new InetSocketAddress(this.host, this.port)) // 绑定连接端口和host信息
                    .handler(new Channel(this.topicCount));
            System.out.println("created..");
            ChannelFuture cf = b.connect().sync(); // 异步连接服务器
            System.out.println("connected..."); // 连接完成
            cf.channel().closeFuture().sync(); // 异步等待关闭连接channel
            System.out.println("closed.."); // 关闭完成
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("无法连接=[" + this.host + ":" + this.port + "] " + e.getMessage());
        } finally {
            try {
                group.shutdownGracefully().sync(); // 释放线程池资源
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Channel extends ChannelInitializer<SocketChannel> { // 绑定连接初始化器

    private TopicCount topicCount;

    public Channel(TopicCount topicCount) {
        this.topicCount = topicCount;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("connected...");
        ch.pipeline().addLast(
                new ObjectEncoder(),
                new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())),
                new EchoClientHandler(this.topicCount)
        );
    }
}