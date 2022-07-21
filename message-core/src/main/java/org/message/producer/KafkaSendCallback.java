package org.message.producer;

import org.message.dto.MessageDTO;
import org.springframework.kafka.support.ProducerListener;

/**
 * <p>
 *  kafka消息发送回调接口
 * </p>
 *
 * @author WECENG
 * @since 2020/12/26 14:59
 */
public interface KafkaSendCallback extends ProducerListener<String, MessageDTO> {
}
