package org.message.client.consumer;

import com.rabbitmq.client.Channel;
import org.message.constant.MQConstant;
import org.message.consumer.MessageReceiverService;
import org.message.dto.MessageDTO;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import static org.springframework.amqp.rabbit.connection.PublisherCallbackChannel.RETURNED_MESSAGE_CORRELATION_KEY;

/**
 * <p>
 * rabbitmq消费者监听器
 * </p>
 *
 * @author WECENG
 * @since 2020/7/29 9:05
 */
@Component("messageReceiver")
@ConditionalOnProperty(prefix = "websocket.mq", name = "type", havingValue = "rabbitmq")
public class RabbitMessageListener {

    @Autowired
    private MessageReceiverService messageReceiverService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MQConstant.DEFAULT_QUEUE),
            exchange = @Exchange(name = MQConstant.DEFAULT_EXCHANGE, type = "topic"),
            key = MQConstant.DEFAULT_ROUTING_KEY
    ))
    @RabbitHandler
    public void onMessage(@Payload String payload, @Headers Map<String, Object> headers, Channel mqChannel) throws IOException {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String messageId = (String) headers.get(RETURNED_MESSAGE_CORRELATION_KEY);
        boolean broadcast = (Boolean) headers.get(MQConstant.HEADER_BROADCAST);
        String topic = (String) headers.get(MQConstant.TOPIC);
        MessageDTO messageDTO = MessageDTO.builder()
                .messageId(messageId)
                .topic(topic)
                .payload(payload)
                .broadcast(broadcast)
                .build();
        messageReceiverService.receive(messageDTO);
        mqChannel.basicAck(deliveryTag, true);
    }


}
