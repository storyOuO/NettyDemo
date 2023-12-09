package com.story.netty.protobufDemo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * Netty测试protobuf传输多种类型 服务端
 * @author story7
 * @date 2023/12/10
 */
public class NettyServer {

    private static final Integer PORT = 7777;

    public static void main(String[] args) throws InterruptedException {

        // bossGroup和workerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 服务器端的启动参数 用于配置
            ServerBootstrap bootstrap = new ServerBootstrap();

            // 配置
            bootstrap.group(bossGroup, workerGroup) // 设置boss和worker两个线程组
                    .channel(NioServerSocketChannel.class) // 设置服务器channel
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置线程保持活动连接
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 解码器 解码客户端发送的数据 需要指定对象
                            pipeline.addLast(new ProtobufDecoder(MyDataInfo.Manager.getDefaultInstance()));
                            pipeline.addLast(new NettyServerHandler());
                        }
                    }); // 管道设置处理器

            System.out.println("服务端 准备就绪.......");

            // 绑定端口进行监听 生成一个ChannelFuture对象 同时启动服务器
            ChannelFuture cf = bootstrap.bind(PORT).sync();

            // 监听到关闭通道的时间 进行回调
            cf.channel().closeFuture().sync();
        } finally {
            // 关闭线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

}
