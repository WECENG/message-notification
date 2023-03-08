package org.message.web.controller;

import org.message.dto.MessageDTO;
import org.message.producer.MessageProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 消息生产者控制器
 * </p>
 *
 * @author WECENG
 * @since 2020/7/23 16:15
 */
@RestController
@RequestMapping("/mq/producer")
@CrossOrigin
@Api(value = "消息管理", tags = "消息管理")
public class ProducerController {

    @Autowired(required = false)
    private MessageProducer messageProducer;


    @PostMapping(value = "/send")
    @ApiOperation(value = "发送消息")
    public void syncSend(@Valid @RequestBody MessageDTO messageDTO) {
        messageProducer.sendMessage(messageDTO);
    }

}
