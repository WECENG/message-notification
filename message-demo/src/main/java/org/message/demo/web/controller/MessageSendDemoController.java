package org.message.demo.web.controller;

import com.alibaba.fastjson.JSON;
import org.message.demo.web.constant.CommonConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.message.dto.MessageDTO;
import org.message.serviceapi.pojo.MessageRuleDetail;
import org.message.producer.MessageProducerService;
import org.message.serviceapi.rule.MessageSubscribeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author WECENG
 * @since 2020年12月31日
 *  测试消息下发入口
 **/
@RestController
@RequestMapping("/message/send")
@Api(value = "消息下发")
public class MessageSendDemoController {

    @Autowired
    private MessageSubscribeRuleService subscribeRuleService;

    @Autowired
    private MessageProducerService messageProducerService;

    @PostMapping
    @ApiOperation("消息下发demo")
    public void doSendMsg() {
        // 封装主题列表
        // 这里需要注意一点，details中需要包含该模块配置时所选的所有详细配置类型
        // 比如，在配置规则时，选择了分公司和角色两种，那么在此处就必须都有，两处要求强匹配（不可多配也不可少配），不然会导致前端订阅的主题与后端发送的主题不同
        List<MessageRuleDetail> details = new ArrayList<>();
        // 分公司
        MessageRuleDetail messageRuleDetailA = new MessageRuleDetail();
        messageRuleDetailA.setKey("tsie");
        messageRuleDetailA.setType(CommonConstants.COMPANY);
        details.add(messageRuleDetailA);
        // 角色
        MessageRuleDetail messageRuleDetaiB = new MessageRuleDetail();
        messageRuleDetaiB.setKey("admin");
        messageRuleDetaiB.setType(CommonConstants.ROLE);
        details.add(messageRuleDetaiB);
        List<String> ruleList = subscribeRuleService.getRuleByParams("trade_plan_year", details);
        // 发送消息
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setBroadcast(false);
        // 可以将前端需要的跳转参数和需要展示的数据放在此处，例如id、消息标题、创建用户，创建时间
        Map<String, Object> params = new HashMap<>();
        params.put("id", "id123123");
        params.put("title", "这是一条测试消息");
        params.put("creator", "创建人");
        params.put("createTime", new Date());
        messageDTO.setPayload(JSON.toJSONString(params));
        if (!CollectionUtils.isEmpty(ruleList)) {
            ruleList.forEach(rule -> {
                messageDTO.setMessageId(UUID.randomUUID().toString().replace("-", "").substring(0,20));
                messageDTO.setTopic(rule);
                messageProducerService.sendMessage(messageDTO);
            });
        }
    }
}
