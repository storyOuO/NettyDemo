package com.story.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;

/**
 * SimpleChannelInboundHandler 继承了 ChannelInboundHandlerAdapter 拥有其所有属性
 * HttpObject 表示http请求的数据封装成HttpObject类型的对象进行传输
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    // channelRead0 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        // 响应http请求
        if (msg instanceof HttpRequest) {

            System.out.println("msg 类型为: " + msg.getClass());
            System.out.println("客户端地址: " + ctx.channel().remoteAddress());

            HttpRequest request = (HttpRequest) msg;
            URI uri = new URI(request.uri());

            if (StringUtils.equals(uri.getPath(), "/favicon.ico")) {
                // 过滤重复请求路径
                System.out.println(uri.getPath() + " 路径重复请求,跳过处理!");
                return;
            }

            // 设置回复内容
            ByteBuf content = Unpooled.copiedBuffer("I have missed u 3000 times", CharsetUtil.UTF_8);

            // 构造Http响应
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            // 放入响应头
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 刷回响应到管道中
            ctx.writeAndFlush(response);

        }

    }

}
