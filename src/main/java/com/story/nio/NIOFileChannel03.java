package com.story.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {

    public static void main(String[] args) throws Exception{

        File file = new File("src/main/resources/test/FileChannel01Test.txt");

        // 如果file存在 获取file中的数据写出
        if (file.isFile()) {

            // 文件输入流
            FileInputStream fis = new FileInputStream(file);
            // 文件输出流
            FileOutputStream fos = new FileOutputStream("src/main/resources/test/FileChannel02Test.txt");

            // 获取channel
            FileChannel fisChannel = fis.getChannel();
            FileChannel fosChannel = fos.getChannel();

            // 建立字节流缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);

            while (true) {

                // 重置缓冲区 防止上次读取的数据影响这次操作
                byteBuffer.clear();

                // 使用channel把数据读到缓冲区中
                int read = fisChannel.read(byteBuffer);

                // 如果没有数据了 退出循环
                if (read == -1) {
                    break;
                }

                // 读写转换!!
                byteBuffer.flip();

                // 把缓冲区中读到的数据写回文件
                fosChannel.write(byteBuffer);
            }

            // 关闭流和通道
            fisChannel.close();
            fosChannel.close();
            fis.close();
            fos.close();

        }

    }

}
