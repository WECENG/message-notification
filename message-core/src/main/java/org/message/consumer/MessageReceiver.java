package org.message.consumer;


import org.message.dto.MessageDTO;

/**
 * <p>
 * 消息消费者接口
 * </p>
 *
 * @author WECENG
 * @since 2020/12/28 13:50
 */
public interface MessageReceiver {

    /**
     * 消费消息
     *
     * @param messageDTO 消息
     */
    void receive(MessageDTO messageDTO);

}
