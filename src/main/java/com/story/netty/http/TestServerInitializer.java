package com.story.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        // 获取管道
        ChannelPipeline pipeline = ch.pipeline();

        // 加入Netty提供的编码解码器
        pipeline.addLast(new HttpServerCodec());
        // 加入自定义的handler
        pipeline.addLast(new TestHttpServerHandler());

    }

}
