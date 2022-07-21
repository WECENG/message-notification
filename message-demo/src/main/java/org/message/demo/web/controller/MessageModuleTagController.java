package org.message.demo.web.controller;

import org.message.web.controller.AbstractMessageModuleTagController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author WECENG
 * @since 2021/2/20 12:50
 */
@RestController
@RequestMapping("/message/module/tag")
@Api(value = "消息订阅模块类型管理")
public class MessageModuleTagController extends AbstractMessageModuleTagController {
    @Override
    public String getCurrentLoginUser() {
        return "admin";
    }
}
