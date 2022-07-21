package org.message.demo.web.controller;

import io.swagger.annotations.Api;
import org.message.web.controller.AbstractMessageLogController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 消息日志管理controller
 * </p>
 *
 * @author chenwc@tsintergy.com
 * @since 2021/2/20 12:50
 */
@RestController
@RequestMapping("/message/log")
@Api(value = "消息日志管理")
public class MessageLogController extends AbstractMessageLogController {

    @Override
    public String getCurrentLoginUser() {
        return "admin";
    }

}
