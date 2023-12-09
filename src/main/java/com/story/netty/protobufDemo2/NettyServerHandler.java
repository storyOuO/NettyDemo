package com.story.netty.protobufDemo2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Netty测试protobuf传输多种类型 服务端处理器
 * @author story7
 * @date 2023/12/10
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.Manager> {

    /**
     * 读入消息
     * @param ctx           the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *                      belongs to
     * @param msg           the message to handle
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Manager msg) throws Exception {
        // 读取客户端发送的数据
        MyDataInfo.Manager manager =  msg;
        MyDataInfo.Manager.DataType dataType = manager.getDataType();
        if (dataType == MyDataInfo.Manager.DataType.LoveType) {
            MyDataInfo.Love love = manager.getLove();
            System.out.println("Lovetime: " + love.getLoveTimes() +" Lovename: " + love.getLoveName());
        } else if (dataType == MyDataInfo.Manager.DataType.HurtType) {
            MyDataInfo.Hurt hurt = manager.getHurt();
            System.out.println("Hurttime: " + hurt.getHurtTimes() +" Hurtname: " + hurt.getHurtName());
        } else {
            System.out.println("传输的类型不正确!");
        }
    }

    /**
     * 发送消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        // writeAndFlush 写入消息到缓冲区 并刷回管道
        // 如果只写入不刷回 消息无法到达客户端
        ctx.writeAndFlush(Unpooled.copiedBuffer("Server: I have missed u 3000 times...", CharsetUtil.UTF_8));

    }

    /**
     * 处理异常 发生异常时手动关闭
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
