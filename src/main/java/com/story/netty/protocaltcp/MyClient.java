package com.story.netty.protocaltcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty测试tcp粘包拆包解决方案 客户端
 * @author story7
 * @date 2023/12/10
 */
public class MyClient {

    private static final String HOST = "127.0.0.1";
    private static final Integer PORT = 7777;

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 服务器端的启动参数 用于配置
            Bootstrap bootstrap = new Bootstrap();

            // 配置
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());

            System.out.println("客户端 准备就绪.......");

            // 绑定端口进行监听 生成一个ChannelFuture对象 同时启动服务器
            ChannelFuture cf = bootstrap.connect(HOST, PORT).sync();
            // 监听到关闭通道的时间 进行回调
            cf.channel().closeFuture().sync();
        } finally {
            // 关闭线程组
            group.shutdownGracefully();
        }
    }

}
