package org.message.server.producer;

import org.message.dto.MessageDTO;
import org.message.producer.MessageProducer;
import org.message.producer.RocketSendCallback;
import org.message.producer.SendCallback;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * <p>
 * rocketmq消息生产者实现类
 * </p>
 *
 * @author WECENG
 * @since 2020/12/25 12:46
 */
@Service("messageProducer")
@ConditionalOnProperty(prefix = "websocket.mq", name = "type", havingValue = "rocketmq")
public class RocketMessageProducer implements MessageProducer {

    @Autowired
    RocketMQTemplate rocketMqTemplate;

    @Value("${rocketmq.topic:}")
    private String topic;

    @Override
    public void sendMessage(MessageDTO messageDTO) {
        rocketMqTemplate.syncSend(topic, messageDTO);
    }

    @Override
    public void sendMessage(MessageDTO messageDTO, SendCallback sendCallback) {
        if (sendCallback instanceof RocketSendCallback) {
            rocketMqTemplate.asyncSend(topic, messageDTO, (RocketSendCallback) sendCallback);
        }else {
            sendMessage(messageDTO);
        }
    }
}
