package org.message.serviceimpl.impl;

import org.message.dto.MessageDTO;
import org.message.producer.MessageProducerService;
import org.message.producer.SendCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * DefaultMessageProducerServiceImpl
 * 当没有实现时，默认实现
 *
 * @author WECENG
 * @since 2021/9/27 20:23
 */
@Service("messageProducerService")
@ConditionalOnProperty(prefix = "websocket.mq", name = "type", havingValue = "false", matchIfMissing = true)
public class DefaultMessageProducerServiceImpl implements MessageProducerService {


    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMessageProducerServiceImpl.class);

    public DefaultMessageProducerServiceImpl() {
        LOGGER.error("未配置websocket.mq.type，无法正常发送mq消息");
    }

    @Override
    public void sendMessage(MessageDTO messageDTO) {
        throw new UnsupportedOperationException("请先配置websocket.mq.type");
    }

    @Override
    public void sendMessage(MessageDTO messageDTO, SendCallback sendCallback) {
        throw new UnsupportedOperationException("请先配置websocket.mq.type");
    }
}
