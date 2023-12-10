package com.story.netty.protocaltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Netty测试tcp粘包拆包解决方案 自定义解码器
 * @author story7
 * @date 2023/12/10
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder decode方法被调用....");
        // 获取编码后的二进制字节码
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);
        // 封装为协议包对象返回
        MessageProtocol msg = new MessageProtocol(len, content);
        out.add(msg);
    }

}
