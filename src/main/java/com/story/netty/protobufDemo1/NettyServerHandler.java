package com.story.netty.protobufDemo1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Netty测试简单的protobuf 客户端处理器
 * @author story7
 * @date 2023/12/10
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 读入消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 读取客户端发送的数据
        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println("接收到客户端发送的StudentPOJO.Student对象 id: " + student.getId() + " ,name: " + student.getName());

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
