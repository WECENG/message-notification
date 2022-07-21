package org.message;

import org.junit.jupiter.api.Test;
import org.message.SpringBootServerApplication;
import org.message.client.netty.NettyWebsocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 * 服务端启动测试类
 * </p>
 *
 * @author WECENG
 * @since 2021/2/2 9:24
 */
@SpringBootTest(classes = SpringBootServerApplication.class)
public class WebsocketServerApplication {

    @Autowired
    private NettyWebsocketServer nettyWebsocketServer;

    @Test
    public void start() {
        nettyWebsocketServer.start();
    }
}
