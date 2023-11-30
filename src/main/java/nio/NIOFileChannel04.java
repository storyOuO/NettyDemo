package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {

    public static void main(String[] args) throws Exception{

        File file = new File("src/main/resources/test/jingggxd.png");

        // 如果file存在 获取file中的数据写出
        if (file.isFile()) {

            // 文件输入流
            FileInputStream fis = new FileInputStream(file);
            // 文件输出流
            FileOutputStream fos = new FileOutputStream("src/main/resources/test/jingggxdCopy.png");

            // 获取channel
            FileChannel fisChannel = fis.getChannel();
            FileChannel fosChannel = fos.getChannel();

            // 调用封装好的transferFrom方法直接转换(零拷贝)
            fosChannel.transferFrom(fisChannel, 0, fisChannel.size());

            // 关闭流和通道
            fisChannel.close();
            fosChannel.close();
            fis.close();
            fos.close();

        }

    }

}
