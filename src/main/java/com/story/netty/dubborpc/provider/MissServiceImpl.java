package com.story.netty.dubborpc.provider;

import com.story.netty.dubborpc.publicinterface.MissService;
import org.apache.commons.lang3.StringUtils;

/**
 * Netty实现dubboRPC 生产者服务接口实现类
 * @author story7
 * @date 2023/12/12
 */
public class MissServiceImpl implements MissService {
    @Override
    public String miss(String msg) {

        // 消费者调用该接口时 返回
        System.out.println("消费者发送消息: " + msg);
        if (StringUtils.isBlank(msg)) {
            return "from server: I have missed u 3000 times.";
        } else {
            return "from server: I have received your message: + " + msg + "\nand I have missed u 3000 times.";
        }
    }
}
