package com.story.netty.inandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Netty测试handler链调用机制 服务端自定义解码器
 * @author story7
 * @date 2023/12/10
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 用long类型来接受数据 即8个字节
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }

    }

}
