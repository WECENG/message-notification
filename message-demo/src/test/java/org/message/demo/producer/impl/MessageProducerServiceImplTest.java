package org.message.demo.producer.impl;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.message.SpringBootServerApplication;
import org.message.dto.MessageDTO;
import org.message.producer.MessageProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author WECENG
 * @since 2021/2/1 17:57
 */
@SpringBootTest(classes = SpringBootServerApplication.class)
public class MessageProducerServiceImplTest {

    @Autowired
    private MessageProducerService messageProducerService;

    @Test
    public void sendMessage() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId("123456111111");
        messageDTO.setTopic("topicA");
        messageDTO.setBroadcast(false);
        Map<String, Object> params = new HashMap<>();
        params.put("id", "id123123");
        params.put("title", "这是一条测试消息");
        params.put("creator", "创建人");
        params.put("createTime", new Date());
        messageDTO.setPayload(JSON.toJSONString(params));
        messageProducerService.sendMessage(messageDTO);
    }

}
