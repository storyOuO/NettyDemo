package com.story.nio;

import java.nio.IntBuffer;

public class BasicBuffer {

    public static void main(String[] args) {

        // 初始化IntBuffer 当前容量为5
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 放入缓存
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i);
        }

        // 读写切换 否则取不出数据!!
        intBuffer.flip();

        // 取出缓存
        while (intBuffer.hasRemaining()) {
            System.out.println("取出缓存: "+intBuffer.get());
        }

    }
}
