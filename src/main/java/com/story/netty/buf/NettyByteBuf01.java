package com.story.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {

    public static void main(String[] args) {

        // 定义一个容量为10的bytebuf 类似于byte[10]
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            // 往buffer对应数组下标写入数据
            buffer.writeByte(i);
        }

        for (int i = 0; i < buffer.capacity(); i++) {
            // 根据下标指定读入数据
//            System.out.println(buffer.getByte(i));
            // 根据readindex顺序读入数据
            System.out.println(buffer.readByte());
        }



    }

}
