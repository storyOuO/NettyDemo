package com.story.netty.protocaltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Netty测试tcp粘包拆包解决方案 客户端自定义处理器
 * @author story7
 * @date 2023/12/10
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler channelActive 方法被调用.....");
        // 循环发送10个ByteBuf
        for (int i = 0; i < 10; i++) {
            MessageProtocol msg = new MessageProtocol();
            msg.setContent(("I have missed u " + i + " times.").getBytes(CharsetUtil.UTF_8));
            msg.setLen(msg.getContent().length);
            ctx.writeAndFlush(msg);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        System.out.println("客户端收到消息: " + new String(msg.getContent(), CharsetUtil.UTF_8));
        System.out.println("客户端收到消息长度: " + msg.getLen());
        System.out.println("客户端收到包次数: " + ++count);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
