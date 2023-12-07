package com.story.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 *  自定义Netty服务器的handler 需要继承 ChannelInboundHandlerAdapter类
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 读入消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 打印ctx
        System.out.println("服务端 ctx: " + ctx);

        // 把读入消息放到缓冲区中 这里使用Netty提供的ByteBuf
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println("客户端发送的消息 msg: " + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址: " + ctx.channel().remoteAddress());
    }

    // 发送消息
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        // writeAndFlush 写入消息到缓冲区 并刷回管道
        // 如果只写入不刷回 消息无法到达客户端
        ctx.writeAndFlush(Unpooled.copiedBuffer("Server: I have missed u 3000 times...", CharsetUtil.UTF_8));

    }

    // 处理异常 发生异常时手动关闭
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
