package nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {

    public static void main(String[] args) throws Exception{

        File file = new File("D:\\Learn\\NettyDemo\\src\\main\\resources\\test\\FileChannel01Test.txt");

        // 如果file存在 获取file中的数据写出
        if (file.isFile()) {

            // 文件输入流
            FileInputStream fis = new FileInputStream(file);

            // 获取channel
            FileChannel fileChannel = fis.getChannel();

            // 建立字节流缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

            // 使用channel把数据读到缓冲区中
            fileChannel.read(byteBuffer);

            // 输出读到的数据
            System.out.println(new String(byteBuffer.array()));

            // 关闭流和通道
            fileChannel.close();
            fis.close();

        }

    }

}
