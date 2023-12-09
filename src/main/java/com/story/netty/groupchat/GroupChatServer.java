package com.story.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GroupChatServer {

    private static final Integer PORT = 7777;

    public GroupChatServer(int port) throws InterruptedException {

        // 工作线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 配置类
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128) // 等待队列最大线程数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 保持连接
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            // 获取管道 加入编解码器和自定义handler
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast(new GroupChatServerHandler());

                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind(port).sync();

            System.out.println("Netty服务器 准备就绪......");

            // 监听关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatServer(PORT);
    }

}
