package org.message.client.netty;

import org.message.dto.WebsocketMes;
import org.message.dto.WebsocketChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>
 * channel管理器，维护channel信息
 * </p>
 *
 * @author WECENG
 * @since 2020/7/25 17:56
 */
@Component("channelManager")
public class ChannelManager {

    /**
     * Channel组
     */
    private final ChannelGroup broadcastGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * Channel的Map
     */
    private final ConcurrentMap<String, ChannelId> channelMap = new ConcurrentHashMap<>(8);

    /**
     * websocket连接信息集合
     */
    private final List<WebsocketChannel> websocketChannelList = new CopyOnWriteArrayList<>();

    /**
     * 添加管道
     *
     * @param channel 管道
     */
    void addChannel(Channel channel) {
        broadcastGroup.add(channel);
        ChannelId channelId = channel.id();
        channelMap.putIfAbsent(channelId.asShortText(), channelId);
    }

    /**
     * 移除管道
     *
     * @param channel 管道
     */
    void removeChannel(Channel channel) {
        broadcastGroup.remove(channel);
        channelMap.remove(channel.id().asShortText());
    }

    /**
     * 注册websocket
     *
     * @param websocketMes websocket连接请求对象
     * @param channel          管道
     */
    void subscribe(WebsocketMes websocketMes, Channel channel) {
        if (CollectionUtils.isEmpty(websocketMes.getTopicList())) {
            return;
        }
        websocketMes.getTopicList().forEach(topic -> {
            WebsocketChannel websocketChannel = websocketChannelList.stream().filter(itemWebsocketChannel -> topic.equals(itemWebsocketChannel.getTopic())).findAny().orElse(null);
            if (Objects.isNull(websocketChannel)) {
                WebsocketChannel newWebsocketChannel = new WebsocketChannel();
                newWebsocketChannel.setTopic(topic);
                Set<String> channelSet = new CopyOnWriteArraySet<>();
                channelSet.add(channel.id().asShortText());
                newWebsocketChannel.setChannelSet(channelSet);
                newWebsocketChannel.setTimestamp(System.currentTimeMillis());
                websocketChannelList.add(newWebsocketChannel);
            } else {
                websocketChannel.setTimestamp(System.currentTimeMillis());
                websocketChannel.getChannelSet().add(channel.id().asShortText());
            }
        });
    }

    /**
     * 注销websocket
     *
     * @param websocketMes websocket连接请求对象
     * @param channel          管道
     */
    void unsubscribe(WebsocketMes websocketMes, Channel channel) {
        if (CollectionUtils.isEmpty(websocketMes.getTopicList())) {
            return;
        }
        websocketMes.getTopicList().forEach(topic -> {
            WebsocketChannel websocketChannel = websocketChannelList.stream().filter(itemWebsocketChannel -> topic.equals(itemWebsocketChannel.getTopic())).findAny().orElse(null);
            if (Objects.isNull(websocketChannel)) {
                return;
            }
            websocketChannel.getChannelSet().removeIf(item -> channel.id().asShortText().equals(item));
            // if channelSet is empty remove websocketChannel
            if (CollectionUtils.isEmpty(websocketChannel.getChannelSet())) {
                websocketChannelList.removeIf(item -> websocketChannel.getTopic().equals(item.getTopic()));
            }
        });
    }

    /**
     * 匹配管道
     *
     * @param channelId 管道id
     * @return
     */
    public Channel findChannel(String channelId) {
        if (StringUtils.isEmpty(channelId)) {
            return null;
        }
        return broadcastGroup.find(channelMap.get(channelId));
    }

    /**
     * 获取websocket连接信息集合
     *
     * @return
     */
    public List<WebsocketChannel> getWebsocketChannelList() {
        return websocketChannelList;
    }

    /**
     * 广播
     *
     * @param text 内容
     */
    public void broadcast(TextWebSocketFrame text) {
        broadcastGroup.writeAndFlush(text);
    }

}
