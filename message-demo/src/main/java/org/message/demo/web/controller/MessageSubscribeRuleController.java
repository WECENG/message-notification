package org.message.demo.web.controller;

import org.message.web.controller.AbstractMessageSubscribeRuleController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author WECENG
 * @since 2021/2/20 12:49
 */
@RestController
@RequestMapping("/message/rule")
@Api(value = "消息订阅规则管理")
public class MessageSubscribeRuleController extends AbstractMessageSubscribeRuleController {

    @Override
    public String getCurrentLoginUser() {
        return "admin";
    }
}
