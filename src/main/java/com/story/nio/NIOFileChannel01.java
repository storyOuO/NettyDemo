package com.story.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {

    public static final String TESTSTR = "I have missed u 3000 times.";

    public static void main(String[] args) throws Exception {

        // FileChannel
        FileOutputStream fos = new FileOutputStream("D:\\Learn\\NettyDemo\\src\\main\\resources\\test\\FileChannel01Test.txt");

        // 用channel与buffer进行交互 实现数据读写
        FileChannel fileChannel = fos.getChannel();

        // 申请容量为1024的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 放入需要写入的字节流
        byteBuffer.put(TESTSTR.getBytes());

        // 读写转换
        byteBuffer.flip();

        // 写回文件
        fileChannel.write(byteBuffer);

        // 关闭流和通道
        fileChannel.close();
        fos.close();

    }
}
