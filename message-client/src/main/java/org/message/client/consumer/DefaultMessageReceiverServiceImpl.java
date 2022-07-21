package org.message.client.consumer;

import org.message.consumer.MessageReceiverService;
import org.message.dto.MessageDTO;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  默认消费者实现类
 *
 *  重写方式（1）：切面将失效 see#org.message.org.message.notification.consumer.MessageReceiverAspect
 * @Primary
 * @Service
 * public class MessageReceiveServiceImpl implements MessageReceiverService {
 *
 *     private final Logger logger = LoggerFactory.getLogger(this.getClass());
 *
 *     @Override
 *     public void receive(MessageRequest messageRequest) {
 *         logger.info("override success");
 *     }
 * }
 *
 * 重写方式（2）：切面依然生效 see#org.message.org.message.notification.consumer.MessageReceiverAspect
 * @Primary
 * @Service
 * public class MessageReceiveServiceImpl extends DefaultMessageReceiverServiceImpl {
 *
 *     private final Logger logger = LoggerFactory.getLogger(this.getClass());
 *
 *     @Override
 *     public void receive(MessageRequest messageRequest) {
 *         logger.info("override success");
 *     }
 * }
 *
 * </p>
 *
 * @author WECENG
 * @since 2020/12/28 14:59
 */
@Component("messageReceiverService")
public class DefaultMessageReceiverServiceImpl implements MessageReceiverService {

    @Override
    public void receive(MessageDTO messageDTO) {
        //do nothing
    }
}
