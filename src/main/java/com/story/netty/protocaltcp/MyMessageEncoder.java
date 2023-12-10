package com.story.netty.protocaltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Netty测试tcp粘包拆包解决方案 自定义编码器
 * @author story7
 * @date 2023/12/10
 */
public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MyMessageEncoder encode方法被调用....");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }

}
