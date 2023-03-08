package org.message.server.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.message.dto.WebsocketMes;
import org.message.dto.WebsocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * websocket处理器
 * </p>
 *
 * @author WECENG
 * @since 2020/7/25 17:16
 */
@Component("nettWebsocketHandler")
@ChannelHandler.Sharable
public class NettyWebsocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static final Logger logger = LoggerFactory.getLogger(NettyWebsocketHandler.class);

    @Autowired
    private ChannelManager channelManager;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        logger.debug("接收客户端信息：{}", msg.text());
        handleWebsocketFrame(ctx, msg);
    }

    private void handleWebsocketFrame(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws JsonProcessingException {
        WebsocketMes websocketMes = objectMapper.readValue(msg.text(), WebsocketMes.class);
        switch (websocketMes.getMesType()) {
            case PINGPONG:
                TextWebSocketFrame pongWebSocketFrame = new TextWebSocketFrame(msg.text());
                ctx.writeAndFlush(pongWebSocketFrame);
                break;
            case SUBSCRIBE:
                channelManager.addChannel(ctx.channel());
                channelManager.subscribe(websocketMes, ctx.channel());
                break;
            case UNSUBSCRIBE:
                channelManager.removeChannel(ctx.channel());
                channelManager.unsubscribe(websocketMes, ctx.channel());
                break;
            default:
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.debug("客户端加入连接：{}", ctx.channel().id().asShortText());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        //Abnormal disconnect close channel
        forceClose(channelManager, ctx.channel());
        logger.debug("客户端断开连接：{}", ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // except connection close channel
        forceClose(channelManager, ctx.channel());
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 强制注销
     *
     * @param channelManager 管道管理器
     * @param channel        管道
     */
    private void forceClose(ChannelManager channelManager, Channel channel) {
        channelManager.removeChannel(channel);
        List<WebsocketChannel> websocketChannelList = channelManager.getWebsocketChannelList()
                .stream()
                .filter(item -> (!CollectionUtils.isEmpty(item.getChannelSet())) && item.getChannelSet().contains(channel.id().asShortText()))
                .collect(Collectors.toList());
        websocketChannelList.forEach(websocketChannel -> {
            WebsocketMes websocketMes = new WebsocketMes();
            websocketMes.setTopicList(Collections.singletonList(websocketChannel.getTopic()));
            channelManager.unsubscribe(websocketMes, channel);
        });
    }

}
