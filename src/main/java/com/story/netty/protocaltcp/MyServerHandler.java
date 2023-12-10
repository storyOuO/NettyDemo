package com.story.netty.protocaltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * Netty测试tcp粘包拆包解决方案 服务端自定义处理器
 * @author story7
 * @date 2023/12/10
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        System.out.println("服务端收到消息: " + new String(msg.getContent(), CharsetUtil.UTF_8));
        System.out.println("服务端收到消息长度: " + msg.getLen());
        System.out.println("服务端收到包次数: " + ++count);

        // 回显一个随机id 封装成MessageProtocol对象返回
        MessageProtocol response = new MessageProtocol();
        response.setContent(UUID.randomUUID().toString().getBytes());
        response.setLen(response.getContent().length);
        ctx.writeAndFlush(response);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
