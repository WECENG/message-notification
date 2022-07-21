package org.message.serviceimpl.rule.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.message.serviceapi.dto.MessageSubscribeRuleDTO;
import org.message.serviceapi.pojo.MessageModuleTagDO;
import org.message.serviceapi.rule.MessageModuleTagService;
import org.message.serviceapi.rule.MessageSubscribeRuleService;
import org.message.serviceimpl.mapper.MessageModuleTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author WECENG
 *
 * @since 2020年10月26日
 **/
@Service
public class MessageModuleTagServiceImpl extends ServiceImpl<MessageModuleTagMapper, MessageModuleTagDO> implements MessageModuleTagService {

    @Autowired
    private MessageSubscribeRuleService ruleService;

    @Override
    public MessageModuleTagDO doCreate(MessageModuleTagDO moduleTagDO) {
        LambdaQueryWrapper<MessageModuleTagDO> queryWrapper = Wrappers.<MessageModuleTagDO>lambdaQuery()
                .eq(StringUtils.isNotEmpty(moduleTagDO.getModuleTag()), MessageModuleTagDO::getModuleTag, moduleTagDO.getModuleTag());
        List<MessageModuleTagDO> moduleTagDOList = list(queryWrapper);
        if (CollectionUtils.isEmpty(moduleTagDOList)) {
            save(moduleTagDO);
        }
        return moduleTagDO;
    }

    @Override
    public MessageModuleTagDO doUpdate(MessageModuleTagDO moduleTagDO) {
        updateById(moduleTagDO);
        return moduleTagDO;
    }

    @Override
    public IPage<MessageModuleTagDO> page(MessageModuleTagDO moduleTagDO, Page<MessageModuleTagDO> page) {
        LambdaQueryWrapper<MessageModuleTagDO> queryWrapper = Wrappers.<MessageModuleTagDO>lambdaQuery()
                .eq(Objects.nonNull(moduleTagDO.getModuleTag()), MessageModuleTagDO::getModuleTag, moduleTagDO.getModuleTag());
        return page(page, queryWrapper);
    }

    @Override
    public List<MessageModuleTagDO> queryList(MessageModuleTagDO moduleTagDO) {
        LambdaQueryWrapper<MessageModuleTagDO> queryWrapper = Wrappers.<MessageModuleTagDO>lambdaQuery()
                .eq(Objects.nonNull(moduleTagDO.getModuleTag()), MessageModuleTagDO::getModuleTag, moduleTagDO.getModuleTag())
                .like(Objects.nonNull(moduleTagDO.getDescription()), MessageModuleTagDO::getDescription, moduleTagDO.getDescription());
        return list(queryWrapper);
    }

    @Override
    public void delete(String id) {
        MessageModuleTagDO moduleTagDO = getById(id);
        if (Objects.nonNull(moduleTagDO)) {
            MessageSubscribeRuleDTO messageSubscribeRuleDTO = ruleService.queryRuleByModuleTag(moduleTagDO.getModuleTag());
            if (Objects.nonNull(messageSubscribeRuleDTO)) {
                ruleService.deleteRule(messageSubscribeRuleDTO.getRuleId());
            }
            removeById(id);
        }
    }


    @Override
    public MessageModuleTagDO queryByModuleTag(String moduleTag) {
        return getOne(Wrappers.<MessageModuleTagDO>lambdaQuery().eq(MessageModuleTagDO::getModuleTag, moduleTag));
    }
}
