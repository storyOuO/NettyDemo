package com.story.netty.dubborpc.server;

import com.story.netty.dubborpc.provider.MissServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Netty实现dubboRPC 服务端server自定义处理器
 * @author story7
 * @date 2023/12/12
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    private static final String DEAL = "MISSJIEJIE :(";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("收到客户端发送的消息: " + msg);
        // 定义协议 判断收到的消息是否符合协议
        if (msg.startsWith(DEAL)) {
            // 如果符合协议 才转发消息
            String sub = msg.substring(DEAL.length());
            // 调用自定义服务接口处理请求
            String res = new MissServiceImpl().miss(sub);
            ctx.writeAndFlush(res);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
