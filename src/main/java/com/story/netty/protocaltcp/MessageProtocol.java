package com.story.netty.protocaltcp;

import lombok.Data;

/**
 * Netty测试tcp粘包拆包解决方案 协议包封装类
 * @author story7
 * @date 2023/12/10
 */
@Data
public class MessageProtocol {
    private int len;
    private byte[] content;

    public MessageProtocol() {

    }

    public MessageProtocol(int len, byte[] content) {
        this.len = len;
        this.content = content;
    }
}
