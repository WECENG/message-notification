package org.message.server.producer;

import org.message.constant.MQConstant;
import org.message.dto.MessageDTO;
import org.message.producer.MessageProducer;
import org.message.producer.RabbitSendCallback;
import org.message.producer.SendCallback;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * rabbitmq消息生产者实现类
 * </p>
 *
 * @author WECENG
 * @since 2020/7/23 14:20
 */
@Service("messageProducer")
@ConditionalOnProperty(prefix = "websocket.mq", name = "type", havingValue = "rabbitmq")
public class RabbitMessageProducer implements MessageProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(MessageDTO messageDTO) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(MQConstant.HEADER_BROADCAST, messageDTO.getBroadcast());
        messageProperties.setHeader(MQConstant.TOPIC, messageDTO.getTopic());
        byte[] messageBytes = messageDTO.getPayload().getBytes(StandardCharsets.UTF_8);
        Message message = new Message(messageBytes, messageProperties);
        CorrelationData correlationData = new CorrelationData(messageDTO.getMessageId());
        rabbitTemplate.send(MQConstant.DEFAULT_EXCHANGE, MQConstant.DEFAULT_ROUTING_KEY, message, correlationData);
    }

    @Override
    public void sendMessage(MessageDTO messageDTO, SendCallback sendCallback) {
        if (!rabbitTemplate.isConfirmListener()) {
            if (sendCallback instanceof RabbitSendCallback){
                rabbitTemplate.setConfirmCallback((RabbitSendCallback)sendCallback);
                rabbitTemplate.setReturnCallback((RabbitSendCallback)sendCallback);
            }
        }
        sendMessage(messageDTO);
    }
}
