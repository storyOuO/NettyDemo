package com.story.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChatClient {

    // 定义变量
    private static final String HOST = "127.0.0.1";
    private static final Integer PORT = 7777;
    private Selector selector;
    private SocketChannel channel;
    private String username;

    // 构造器 初始化变量
    public GroupChatClient() {

        try {
            // 获取selector
            selector = Selector.open();
            // 连接服务器
            channel = channel.open(new InetSocketAddress(HOST, PORT));
            // 设置channel为非阻塞
            channel.configureBlocking(false);
            // 将channel注册到selector中 进行读操作
            channel.register(selector, SelectionKey.OP_READ);
            // 获取username
            username = channel.getLocalAddress().toString().substring(1);
            System.out.println("客户端: " + username + " 准备就绪...........");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // 发送消息给服务器
    public void sendMsg(String msg) {

        msg = username + ":" + msg;

        try {
            channel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 从服务器接收消息
    public void readMsg() {

        try {
            // 阻塞直到有可用通道
            int count = selector.select();
            if (count > 0) {

                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

                while (keyIterator.hasNext()) {

                    SelectionKey key = keyIterator.next();
                    // 获取频道和buffer 读到buffer中
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    // 设置频道为非阻塞
                    socketChannel.configureBlocking(false);

                    // 如果当前key可读 获取通道中的数据
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.read(buffer);
                        // 输出缓存区的信息
                        String msg = new String(buffer.array());
                        System.out.println("客户端: " + username + " 接收到消息: " + msg.trim());
                    }

                }

                keyIterator.remove();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        // 启动客户端
        GroupChatClient client = new GroupChatClient();

        // 启动一个线程循环读数据
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    client.readMsg();
                    try {
                        // 每隔3秒读一次数据
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            client.sendMsg(scanner.nextLine());
        }

    }

}
