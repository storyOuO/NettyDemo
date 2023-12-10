package com.story.netty.inandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Netty测试handler链调用机制 服务端自定义解码器
 * 参数指定用户状态管理的类型 为void表示不需要状态管理
 * @author story7
 * @date 2023/12/10
 */
public class MyByteToLongReplayDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("MyByteToLongReplayDecoder decode被调用.....");
        // 无需判断 直接加入list
        out.add(in.readLong());

    }

}
