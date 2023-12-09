package com.story.netty.heartbeat;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 上下文事件处理器
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {

            // 向下转型
            IdleStateEvent event = (IdleStateEvent) evt;

            String eventType = null;

            if (event.state() == IdleState.READER_IDLE) {
                eventType = "读空闲...";
            } else if (event.state() == IdleState.WRITER_IDLE) {
                eventType = "写空闲...";
            } else if (event.state() == IdleState.ALL_IDLE) {
                eventType = "读写空闲...";
            }

            System.out.println("客户端 " + ctx.channel().remoteAddress() + " 发生空闲: " + eventType);

        }

    }
}
