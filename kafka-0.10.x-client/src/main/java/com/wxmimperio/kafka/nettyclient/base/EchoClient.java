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
import java.util.Map;


/**
 * Created by weiximing.imperio on 2017/1/4.
 */

public class EchoClient {
    private final String host;
    private final int port;
    private Map<String,Long> topicPartitionCount;

    public EchoClient(String host, int port,Map<String,Long> topicPartitionCount) {
        super();
        this.host = host;
        this.port = port;
        this.topicPartitionCount = topicPartitionCount;
    }

    public boolean send() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group) // 注册线程池
                    .channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
                    .remoteAddress(new InetSocketAddress(this.host, this.port)) // 绑定连接端口和host信息
                    .handler(new Channel(topicPartitionCount));
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

    private Map<String,Long> topicPartitionCount;

    public Channel(Map<String,Long> topicPartitionCount) {
        this.topicPartitionCount = topicPartitionCount;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("connected...");
        ch.pipeline().addLast(
                new ObjectDecoder(1024 * 1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())),
                new ObjectEncoder(),
                new EchoClientHandler(this.topicPartitionCount)
        );
    }
}