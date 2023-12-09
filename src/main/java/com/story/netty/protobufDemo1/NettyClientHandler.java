package com.story.netty.protobufDemo1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Netty测试简单的protobuf 客户端处理器
 * @author story7
 * @date 2023/12/10
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 使用protobuf生成一个对象发送到服务器
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 生成StudentPOJO的Student对象
        StudentPOJO.Student jiejie = StudentPOJO.Student.newBuilder().setId(4).setName("Jiejie").build();
        ctx.writeAndFlush(jiejie);

    }

    /**
     * 通道监听到读事件时 触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端发送的消息: " + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址: " + ctx.channel().remoteAddress());
    }

    /**
     * 异常事件处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
