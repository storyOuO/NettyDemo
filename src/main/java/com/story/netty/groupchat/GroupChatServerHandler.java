package com.story.netty.groupchat;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 构造一个单例的channelgroup 用于管理服务器中所有频道
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // 日期格式
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 监听add事件 即建立连接
    // 发送给客户端的消息
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 利用Netty封装好的 channelGroup类 直接发送消息给其他频道
        channelGroup.writeAndFlush("[客户端]:" + ctx.channel().remoteAddress() + " 加入聊天...\n");
        channelGroup.add(ctx.channel());
    }

    // removed时间 即断开连接
    // 发送给客户端的消息
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush("[客户端]:" + ctx.channel().remoteAddress() + " 离开聊天...\n");
        // channelGroup会自动删除 无需手动删除频道
    }

    // active 即连接状态
    // 服务端的监听
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了.....\n");
    }

    // active 即断开连接状态
    // 服务端的监听
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 下线了.....\n");
    }

    // 进行消息转发
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        // 消息来源channel
        Channel channel = ctx.channel();
        // 根据是否消息来源显示不同信息
        channelGroup.forEach(ch -> {
            if (ch.equals(channel)) {
                ch.writeAndFlush(sdf.format(new Date()) + " 我 : " + msg + "\n");
            } else {
                ch.writeAndFlush(sdf.format(new Date()) + " [用户]" + channel.remoteAddress() + " : " + msg + "\n");
            }
        });

    }

    // 异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭ctx
        ctx.close();
    }
}
