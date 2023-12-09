package com.story.netty.websocket;

import com.story.netty.heartbeat.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;;

/**
 * Netty websocket 服务器class
 * @author story7
 * @date 2023/12/09
 */
public class MyServer {

    private static final Integer PORT = 7777;

    public MyServer() throws InterruptedException {

        // 工作线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 配置类
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // websocket使用Http协议 所以使用http编解码器
                            pipeline.addLast(new HttpServerCodec());
                            // websocket用块的形式写入 添加块处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            // 由于分为帧传输 所以需要聚合分帧的处理器
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            // 将指定请求的http协议升级为ws协议
                            pipeline.addLast(new WebSocketServerProtocolHandler("/miss"));
                            // 自定义handler
                            pipeline.addLast(new MyWebsocketHandler());
                        }
                    });

            // 获取channelFuture 监听结束事件
            ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new MyServer();
    }


}
