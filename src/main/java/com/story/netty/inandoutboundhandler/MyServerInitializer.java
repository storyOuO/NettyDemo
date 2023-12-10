package com.story.netty.inandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Netty测试handler链调用机制 服务端自定义初始化类
 * @author story7
 * @date 2023/12/10
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //(出站-编码  入站-解码)
        // 自定义解码器
//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongReplayDecoder());
        // 自定义编码器
        pipeline.addLast(new MyLongToByteEncoder());
        // 自定义业务处理器
        pipeline.addLast(new MyServerHandler());
    }
}
