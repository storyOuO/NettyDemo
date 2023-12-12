package com.story.netty.dubborpc.provider;

import com.story.netty.dubborpc.server.NettyServer;

/**
 * Netty实现dubboRPC 服务端server启动类
 * 在该类中 会启动NettyServer
 * @author story7
 * @date 2023/12/12
 */
public class ServerBoostrap {

    private static final String HOST = "127.0.0.1";
    private static final Integer PORT = 7777;

    public static void main(String[] args) {

        NettyServer.startServer(HOST, PORT);

    }

}
