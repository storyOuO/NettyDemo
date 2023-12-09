package com.story.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class NettyByteBuf02 {

    public static void main(String[] args) {

        // 获取缓冲区
        ByteBuf byteBuf = Unpooled.copiedBuffer("I have missed u 3000 times.", CharsetUtil.UTF_8);

        if (byteBuf.hasArray()) {
            // 获取缓冲区内容
            byte[] content = byteBuf.array();
            System.out.println(new String(content, CharsetUtil.UTF_8).trim());
        }

    }

}
