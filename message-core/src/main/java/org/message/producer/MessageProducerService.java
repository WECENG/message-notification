package org.message.producer;


import org.message.dto.MessageDTO;

/**
 * <p>
 * 消息生产者接口
 * </p>
 *
 * @author WECENG
 * @since 2020/7/23 12:39
 */
public interface MessageProducerService {
    /**
     * 发送消息
     *
     * @param messageDTO 消息请求对象
     */
    void sendMessage(MessageDTO messageDTO);

    /**
     * 发送消息回调
     *
     * @param messageDTO 消息请求对象
     * @param sendCallback   回调
     */
    void sendMessage(MessageDTO messageDTO, SendCallback sendCallback);

}
