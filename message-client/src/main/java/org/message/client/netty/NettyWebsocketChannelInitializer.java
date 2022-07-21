package org.message.client.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 * channel的初始化类
 * </p>
 *
 * @author WECENG
 * @since 2020/7/25 17:05
 */
@Component("nettyWebsocketChannelInitializer")
public class NettyWebsocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Value("${websocket.uri}")
    private String uri;

    @Autowired
    private NettyWebsocketHandler nettyWebsocketHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        /**
         * 日志处理器，http编解码器，报文聚合处理器，报文分块传输处理器，websocket协议编解码器，自定义websocket处理器
         */
        ch.pipeline()
                .addLast("logging", new LoggingHandler("DEBUG"))
                .addLast("http-codec", new HttpServerCodec())
                .addLast("aggregator", new HttpObjectAggregator(65535))
                .addLast("chuncked", new ChunkedWriteHandler())
                .addLast("websocketcodec", new WebSocketServerProtocolHandler(uri))
                .addLast("websockethandler", nettyWebsocketHandler);
    }
}
