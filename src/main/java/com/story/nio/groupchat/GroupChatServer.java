package com.story.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 *  群聊demo-服务端
 */
public class GroupChatServer {

    // 定义变量
    private Selector selector;
    private ServerSocketChannel listener;
    private static final Integer PORT = 7777;

    // 初始化构造器
    public GroupChatServer() {

        try {
            // 初始化
            selector = Selector.open();
            listener = ServerSocketChannel.open();
            // 获取套接字 绑定端口
            listener.socket().bind(new InetSocketAddress(PORT));
            // nio 设置非阻塞
            listener.configureBlocking(false);
            // 将selector注册到监听器 监听注册事件
            listener.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 监听
    public void listen() {

        try {

            while (true) {

                // 每2秒获取一次数据
                int count = selector.select(2000);

                if (count > 0) {

                    // 获取到了数据 selectedKeys遍历所有等待io操作或者监听到事件的selectionKey
                    Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                    while (selectionKeyIterator.hasNext()) {

                        // 当前事件的key
                        SelectionKey key = selectionKeyIterator.next();

                        if (key.isAcceptable()) {
                            // 已知是acceptable 直接accept获取频道
                            SocketChannel channel = listener.accept();
                            // 设置频道为非阻塞
                            channel.configureBlocking(false);
                            // 将channel注册到selector中 接收读事件
                            channel.register(selector, SelectionKey.OP_READ);

                            // 输出消息
                            System.out.println("IP: " + channel.getRemoteAddress() + " 用户上线了........");
                        }

                        if (key.isReadable()) {
                            // read事件 读入数据到缓存中 再输出...
                            read(key);
                        }

                        // 操作完毕 移出当前key
                        selectionKeyIterator.remove();

                    }

                } else {
                    System.out.println("服务端等待中............");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 读数据方法
    private void read(SelectionKey key) {

        // 根据key获取对应channel
        SocketChannel channel = (SocketChannel) key.channel();

        try {

            // 申请缓冲区 读入数据
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);

            if (count > 0) {

                // 读到了数据 输出
                String msg = new String(buffer.array());
                System.out.println("读到数据: " + msg);

                // 转发到其他客户端
                sendMsg(msg, channel);

            }


        } catch (IOException e) {

            try {
                // 如果抛出异常 代表当前key对应chennel已离线
                // 因为key是从服务端拿的 所以拿远程 也就是对端地址输出
                System.out.println("IP:" + channel.getRemoteAddress() + " 离线了......");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }

    }

    /**
     * 消息转发方法
     * @param msg 待转发的消息
     * @param originChannel 当前channel
     */
    private void sendMsg(String msg, SocketChannel originChannel) throws IOException {

        System.out.println("服务端转发消息中........");

        // 遍历所有注册到selector的keys 排除当前channel
        for (SelectionKey key : selector.keys()) {

            Channel channel = key.channel();

            // 如果是socketchannel才转发 且排除当前channel
            if (channel instanceof SocketChannel && channel != originChannel) {

                SocketChannel destChannel = (SocketChannel) channel;
                // 将msg存到buffer中 写入channel
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                destChannel.write(buffer);

            }

        }

    }

    public static void main(String[] args) {

        GroupChatServer server = new GroupChatServer();

        server.listen();

    }

}
