package org.message;

import org.message.server.netty.NettyWebsocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 在启动服务的同时打开websocket
 *
 * @author WECENG
 * @since 2020年12月31日
 **/
@SpringBootApplication
@EnableAsync
public class WebsocketServerApplication implements ApplicationRunner {

    @Autowired
    private NettyWebsocketServer nettyWebsocketServer;

    public static void main(String[] args) {
        SpringApplication.run(WebsocketServerApplication.class, args);
    }

    @Override
    @Async
    public void run(ApplicationArguments args) throws Exception {
        nettyWebsocketServer.start();
    }
}
