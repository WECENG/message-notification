package org.message.server.receiver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.message.dto.MessageDTO;
import org.message.server.netty.ChannelManager;
import org.message.serviceapi.dto.MessageLogRespDTO;
import org.message.serviceapi.enums.MesLogStatusEnum;
import org.message.serviceapi.pojo.MessageLogDO;
import org.message.dto.WebsocketChannel;
import org.message.serviceapi.record.MessageLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 消息接收切面类
 * </p>
 *
 * @author WECENG
 * @since 2020/12/28 10:48
 */
@Component
@Aspect
public class MessageReceiverAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageLogService messageLogService;

    @Autowired
    private ChannelManager channelManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut("execution(* org.message.server.receiver.DefaultMessageReceiver.receive(..))")
    public void pointcut() {
    }

    @Before(value = "pointcut() && args(messageDTO)")
    public void before(MessageDTO messageDTO) {
        MessageLogDO messageLogDO = new MessageLogDO();
        messageLogDO.setMessageId(messageDTO.getMessageId());
        messageLogDO.setPayload(messageDTO.getPayload());
        messageLogDO.setBroadcast(messageDTO.getBroadcast());
        messageLogDO.setTopic(messageDTO.getTopic());
        messageLogDO.setCreateTime(new Date());
        messageLogDO.setUpdateTime(new Date());
        messageLogService.saveOrUpdate(messageLogDO);
    }

    @After(value = "pointcut() && args(messageDTO)")
    public void after(MessageDTO messageDTO) throws JsonProcessingException {
        MessageLogRespDTO messageLogRespDTO = new MessageLogRespDTO();
        messageLogRespDTO.setMessageId(messageDTO.getMessageId());
        messageLogRespDTO.setPayload(messageDTO.getPayload());
        messageLogRespDTO.setBroadcast(messageDTO.getBroadcast());
        messageLogRespDTO.setTopic(messageDTO.getTopic());
        messageLogRespDTO.setStatus(MesLogStatusEnum.UNREAD);
        messageLogRespDTO.setCreateTime(new Date());
        String messageLogJsonStr = objectMapper.writeValueAsString(messageLogRespDTO);
        if (messageDTO.getBroadcast()) {
            TextWebSocketFrame webSocketFrame = new TextWebSocketFrame(messageLogJsonStr);
            channelManager.broadcast(webSocketFrame);
        } else {
            //abandon error message
            if (Objects.isNull(messageDTO.getTopic())) {
                logger.error("topic is required when the value of broadcast is false, abandon the message:{}", messageDTO.getMessageId());
            } else {
                WebsocketChannel websocketChannel = channelManager.getWebsocketChannelList().stream().filter(item -> messageDTO.getTopic().equals(item.getTopic())).findAny().orElse(null);
                //online
                if (Objects.nonNull(websocketChannel)) {
                    websocketChannel.getChannelSet().forEach(item -> {
                        Channel channel = channelManager.findChannel(item);
                        if (Objects.nonNull(channel)) {
                            TextWebSocketFrame webSocketFrame = new TextWebSocketFrame(messageLogJsonStr);
                            channel.writeAndFlush(webSocketFrame);
                        }
                    });
                }
            }
        }
    }

}
