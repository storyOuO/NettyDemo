package com.story.netty.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty测试tcp粘包拆包 服务端
 * @author story7
 * @date 2023/12/10
 */
public class MyServer {

    private static final Integer PORT = 7777;

    public static void main(String[] args) throws InterruptedException {
        // bossGroup和workerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 服务器端的启动参数 用于配置
            ServerBootstrap bootstrap = new ServerBootstrap();

            // 配置
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());

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
