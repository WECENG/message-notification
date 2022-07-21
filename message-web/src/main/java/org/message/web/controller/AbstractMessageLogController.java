package org.message.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.message.serviceapi.dto.MessageLogReqDTO;
import org.message.serviceapi.dto.MessageLogRespDTO;
import org.message.serviceapi.pojo.MessageLogDO;
import org.message.serviceapi.pojo.MessageLogReadDO;
import org.message.serviceapi.record.MessageLogReadService;
import org.message.serviceapi.rule.MessageSubscribeRuleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 消息历史记录controller
 * </p>
 *
 * @author WECENG
 * @since 2020/10/18 17:41
 */
public abstract class AbstractMessageLogController extends AbstractCurrentUserController {

    @Autowired(required = false)
    private MessageLogReadService messageLogReadService;

    @Autowired(required = false)
    private MessageSubscribeRuleService ruleService;


    @GetMapping("/page")
    @ApiOperation("列表查询")
    public IPage<MessageLogRespDTO> listGet(MessageLogReqDTO messageLogReqDTO) {
        // 查询当前用户订阅主题列表
        List<String> ruleDOList = ruleService.queryRuleByUserId(getCurrentLoginUser());
        messageLogReqDTO.setTopicList(ruleDOList);
        // 查询消息
        Page<MessageLogDO> page = new Page<>();
        BeanUtils.copyProperties(messageLogReqDTO, page);
        return messageLogReadService.queryList(messageLogReqDTO, page);
    }

    @PostMapping("/page")
    @ApiOperation("列表查询")
    public IPage<MessageLogRespDTO> listPost(@RequestBody @Valid MessageLogReqDTO messageLogReqDTO) {
        // 查询当前用户订阅主题列表
        List<String> ruleDOList = ruleService.queryRuleByUserId(getCurrentLoginUser());
        messageLogReqDTO.setTopicList(ruleDOList);
        // 查询消息
        Page<MessageLogDO> page = new Page<>();
        BeanUtils.copyProperties(messageLogReqDTO, page);
        return messageLogReadService.queryList(messageLogReqDTO, page);
    }

    @PostMapping("/read")
    @ApiOperation("标记已读")
    public void updateStatus(@RequestBody MessageLogReadDO messageLogReadDO) {
        messageLogReadDO.setCreateTime(new Date());
        messageLogReadDO.setUpdateTime(new Date());
        messageLogReadService.saveOrUpdate(messageLogReadDO);
    }

}
