package com.story.netty.protobufDemo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * Netty测试简单的protobuf 客户端
 * @author story7
 * @date 2023/12/10
 */
public class NettyClient {

    private static final Integer PORT = 7777;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) throws InterruptedException {

        // 线程组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            // 配置类
            Bootstrap bootstrap = new Bootstrap();

            // 和server类似配置
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 编码器 编码发送给服务器
                            pipeline.addLast(new ProtobufEncoder());
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("客户端 准备就绪...........");

            // 连接到服务器
            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();

            // 监听通道是否关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }

    }

}
