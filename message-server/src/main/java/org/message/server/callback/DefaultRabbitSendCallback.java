package org.message.server.callback;

import org.message.producer.RabbitSendCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 默认rabbitmq消息发送回调
 * </p>
 *
 * @author WECENG
 * @since 2020/10/15 22:17
 */
public class DefaultRabbitSendCallback implements RabbitSendCallback {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            logger.error("发送消息失败：时间{}，消息唯一标识{}，失败原因：{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), correlationData.getId(), cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

    }
}
