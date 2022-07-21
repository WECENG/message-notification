package org.message.web.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.message.serviceapi.dto.MessageSubscribeRuleDTO;
import org.message.serviceapi.dto.MessageSubscribeRuleDetailDTO;
import org.message.serviceapi.pojo.MessageSubscribeRuleDO;
import org.message.serviceapi.rule.MessageSubscribeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author WECENG
 * @since 2020年10月23日
 *
 **/
public abstract class AbstractMessageSubscribeRuleController extends AbstractCurrentUserController{

    @Autowired(required = false)
    private MessageSubscribeRuleService ruleService;

    @PostMapping
    @ApiOperation(value = "新增或修改规则")
    public String doCreateOrUpdateRule(@RequestBody @Valid MessageSubscribeRuleDTO ruleDTO) {
        String userId = getCurrentLoginUser();
        String ruleId = ruleDTO.getRuleId();
        if (StringUtils.isNotEmpty(ruleId)){
            MessageSubscribeRuleDO messageSubscribeRuleDO = ruleService.doUpdateRule(ruleDTO, userId);
            ruleId = messageSubscribeRuleDO.getId();
        } else {
            MessageSubscribeRuleDO messageSubscribeRuleDO = ruleService.doCreateRule(ruleDTO, userId);
            ruleId = messageSubscribeRuleDO.getId();
        }
        return ruleId;
    }

    @DeleteMapping("/deleteRule/{ruleId}")
    @ApiOperation(value = "删除规则")
    public void deleteRule(@PathVariable("ruleId") String ruleId) {
        ruleService.deleteRule(ruleId);
    }

    @PostMapping("/bindUser")
    @ApiOperation(value = "新增、编辑规则人员配置")
    public void doCreateOrUpdateRuleDetail(@RequestBody @Valid MessageSubscribeRuleDetailDTO detailDTO) {
        ruleService.doCreateOrUpdateRuleDetail(detailDTO, getCurrentLoginUser());
    }

    @GetMapping("/queryRuleByUser")
    @ApiOperation(value = "根据用户查询规则列表")
    public List<String> queryRuleByUser() {
        String userId = getCurrentLoginUser();
        return ruleService.queryRuleByUserId(userId);
    }

}
