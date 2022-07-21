package org.message.producer;

import org.message.producer.SendCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * <p>
 * rabbitmq消息发送回调
 * </p>
 *
 * @author WECENG
 * @since 2020/7/24 11:03
 */
public interface RabbitSendCallback extends SendCallback, RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback {

}
