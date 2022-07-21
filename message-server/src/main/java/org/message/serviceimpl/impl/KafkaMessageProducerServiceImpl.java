package org.message.serviceimpl.impl;

import org.message.dto.MessageDTO;
import org.message.producer.KafkaSendCallback;
import org.message.producer.MessageProducerService;
import org.message.producer.SendCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * kafka消息生产者实现类
 * </p>
 *
 * @author WECENG
 * @since 2020/12/26 14:30
 */
@Service("messageProducerService")
@ConditionalOnProperty(prefix = "websocket.mq", name = "type", havingValue = "kafka")
public class KafkaMessageProducerServiceImpl implements MessageProducerService {

    @Autowired
    KafkaTemplate<String, MessageDTO> kafkaTemplate;

    @Value("${spring.kafka.topic:}")
    private String topic;

    @Override
    public void sendMessage(MessageDTO messageDTO) {
        kafkaTemplate.send(topic, messageDTO);
    }

    @Override
    public void sendMessage(MessageDTO messageDTO, SendCallback sendCallback) {
        if (sendCallback instanceof KafkaSendCallback) {
            kafkaTemplate.setProducerListener((KafkaSendCallback) sendCallback);
        }
        sendMessage(messageDTO);
    }

}
