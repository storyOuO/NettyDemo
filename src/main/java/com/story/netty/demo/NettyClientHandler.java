package com.story.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    // 通道就绪时 触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端 ctx: " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("Client: I have missed u 4000 times....", CharsetUtil.UTF_8));
    }

    // 通道监听到读事件时 触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端发送的消息: " + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址: " + ctx.channel().remoteAddress());
    }

    // 异常事件处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
