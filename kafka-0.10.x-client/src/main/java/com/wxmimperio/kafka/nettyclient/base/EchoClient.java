package com.wxmimperio.kafka.nettyclient.base;

import com.wxmimperio.kafka.pojo.TopicCount;
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

public class EchoClient {
    private final String host;
    private final int port;
    private TopicCount topicCount;

    public EchoClient(String host, int port, TopicCount topicCount) {
        super();
        this.host = host;
        this.port = port;
        this.topicCount = topicCount;
    }

    public boolean send() {
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
            return false;
        } finally {
            try {
                group.shutdownGracefully().sync(); // 释放线程池资源
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
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
                new ObjectDecoder(1024 * 1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())),
                new ObjectEncoder(),
                new EchoClientHandler(this.topicCount)
        );
    }
}