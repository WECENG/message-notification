package org.message.client.consumer;

import org.message.consumer.MessageReceiverService;
import org.message.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * kafka消费者监听器
 * </p>
 *
 * @author WECENG
 * @since 2020/12/26 11:28
 */
@Component("messageReceiver")
@ConditionalOnProperty(prefix = "websocket.mq", name = "type", havingValue = "kafka")
public class KafkaMessageListener{

    @Autowired
    private MessageReceiverService messageReceiverService;

    @KafkaListener(topicPartitions = {@TopicPartition(topic = "${spring.kafka.topic}", partitions = "${spring.kafka.partition:0}")})
    public void onMessage(List<MessageDTO> messageDTOList) {
        messageDTOList.forEach(messageRequest -> messageReceiverService.receive(messageRequest));
    }

}
