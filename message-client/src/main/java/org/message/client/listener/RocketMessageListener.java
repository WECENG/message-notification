package org.message.client.listener;

import org.message.consumer.MessageReceiver;
import org.message.dto.MessageDTO;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * <p>
 * rocketmq消费者监听器
 * </p>
 *
 * @author WECENG
 * @since 2020/12/25 10:24
 */
@Component("messageListener")
@ConditionalOnProperty(prefix = "websocket.mq", name = "type", havingValue = "rocketmq")
@RocketMQMessageListener(nameServer = "${rocketmq.name-server}", topic = "${rocketmq.topic}", consumerGroup = "${rocketmq.consumer.group}")
public class RocketMessageListener implements RocketMQListener<MessageDTO> {

    @Autowired
    private MessageReceiver messageReceiver;

    @Override
    public void onMessage(MessageDTO messageDTO) {
        messageReceiver.receive(messageDTO);
    }


}
