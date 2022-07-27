package org.message.client.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * <p>
 * websocket服务
 * </p>
 *
 * @author WECENG
 * @since 2020/7/25 16:47
 */
@Component("nettyWebsocketServer")
@ConditionalOnProperty(prefix = "websocket.server", name = "enable", havingValue = "true")
public class NettyWebsocketServer {

    @Autowired
    private NettyWebsocketChannelInitializer nettyWebsocketChannelInitializer;

    @Value("${websocket.port:8085}")
    private Integer port;

    @Value(("${netty.bossGroupSize:1}"))
    private Integer bossGroupSize;

    @Value(("${netty.workerGroupSize:8}"))
    private Integer workerGroupSize;

    private final static Logger logger = LoggerFactory.getLogger(NettyWebsocketServer.class);

    public void start() {
        logger.info("启动websocket服务...");
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(bossGroupSize);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(workerGroupSize);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(nettyWebsocketChannelInitializer)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            Channel channel = bootstrap.bind(port).sync().channel();
            logger.info("websocket服务启动正常。");
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("websocket服务启动异常");
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
