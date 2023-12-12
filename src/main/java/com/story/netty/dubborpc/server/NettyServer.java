package com.story.netty.dubborpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Netty实现dubboRPC 服务端server实现类
 * @author story7
 * @date 2023/12/12
 */
public class NettyServer {

    // 对外暴露的初始化方法 便于在不同情况下调用多个初始化类
    public static void startServer(String host, int port) {
        startServer0(host,port);
    }

    // 初始化NettyServer
    private static void startServer0(String host, int port) {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 服务实现类返回String类型 所以用String编解码器即可
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            // 自定义处理器
                            pipeline.addLast(null);
                        }
                    });

            // 把异步转为同步监听(即阻塞
            ChannelFuture channelFuture = bootstrap.bind(host, port).sync();
            System.out.println("服务端准备就绪......");
            channelFuture.channel().closeFuture().sync();

        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
