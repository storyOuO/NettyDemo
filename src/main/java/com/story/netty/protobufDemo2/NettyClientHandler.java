package com.story.netty.protobufDemo2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * Netty测试protobuf传输多种类型 客户端处理器
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

        // 随机发送Love或者Hurt对象
        int random = new Random().nextInt(3);
        MyDataInfo.Manager manager = null;
        // 根据random随机生成对象发送
        if (random == 0) {
            MyDataInfo.Love jiejie = MyDataInfo.Love.newBuilder().setLoveTimes(3000).setLoveName("Jiejie").build();
            manager = MyDataInfo.Manager.newBuilder().setDataType(MyDataInfo.Manager.DataType.LoveType).setLove(jiejie).build();
        } else {
            MyDataInfo.Hurt story = MyDataInfo.Hurt.newBuilder().setHurtTimes(3000).setHurtName("story").build();
            manager = MyDataInfo.Manager.newBuilder().setDataType(MyDataInfo.Manager.DataType.HurtType).setHurt(story).build();
        }
        ctx.writeAndFlush(manager);

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
