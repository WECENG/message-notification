package org.message.serviceimpl.rule.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.message.serviceapi.constant.MessageConstants;
import org.message.serviceapi.dto.MessageRuleUserDTO;
import org.message.serviceapi.dto.MessageSubscribeRuleDTO;
import org.message.serviceapi.dto.MessageSubscribeRuleDetailDTO;
import org.message.serviceapi.pojo.MessageRuleDetail;
import org.message.serviceapi.pojo.MessageSubscribeRuleDO;
import org.message.serviceapi.pojo.MessageSubscribeRuleUserDO;
import org.message.serviceapi.rule.MessageRuleUserService;
import org.message.serviceapi.rule.MessageSubscribeRuleService;
import org.message.serviceapi.rule.MessageSubscribeRuleUserService;
import org.message.serviceimpl.mapper.MessageSubscribeRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WECENG
 *
 * @since 2020年10月23日
 **/
@Service
public class MessageSubscribeRuleServiceImpl extends ServiceImpl<MessageSubscribeRuleMapper, MessageSubscribeRuleDO> implements MessageSubscribeRuleService {

    @Autowired
    private MessageSubscribeRuleUserService subscribeRuleUserService;

    @Autowired(required = false)
    private MessageRuleUserService messageRuleUserService;

    @Override
    public MessageSubscribeRuleDO doCreateRule(MessageSubscribeRuleDTO ruleDTO, String userId) {
        MessageSubscribeRuleDO ruleDO = new MessageSubscribeRuleDO();
        ruleDO.setModuleTag(ruleDTO.getModuleTag());
        LambdaQueryWrapper<MessageSubscribeRuleDO> queryWrapper = getRuleQueryWrapper(ruleDO);
        MessageSubscribeRuleDO subscribeRuleDO = getOne(queryWrapper);
        if (Objects.isNull(subscribeRuleDO)) {
            ruleDO.setCreatorId(userId);
            ruleDO.setCreateTime(new Date());
            ruleDO.setModuleTag(ruleDTO.getModuleTag());
            ruleDO.setRule(ruleDTO.getDetails());
            ruleDO.setStrongMatch(ruleDTO.getStrongMatch());
            save(ruleDO);
            return ruleDO;
        }
        return subscribeRuleDO;
    }

    @Override
    public MessageSubscribeRuleDO doUpdateRule(MessageSubscribeRuleDTO ruleDTO, String userId) {
        MessageSubscribeRuleDO ruleDO = new MessageSubscribeRuleDO();
        ruleDO.setId(ruleDTO.getRuleId());
        ruleDO.setUpdaterId(userId);
        ruleDO.setUpdateTime(new Date());
        ruleDO.setModuleTag(ruleDTO.getModuleTag());
        ruleDO.setRule(ruleDTO.getDetails());
        ruleDO.setStrongMatch(ruleDTO.getStrongMatch());
        saveOrUpdate(ruleDO);
        return ruleDO;
    }


    @Override
    public MessageSubscribeRuleDTO queryRuleByModuleTag(String moduleTag) {
        MessageSubscribeRuleDTO ruleDTO = new MessageSubscribeRuleDTO();
        MessageSubscribeRuleDO ruleDO = new MessageSubscribeRuleDO();
        ruleDO.setModuleTag(moduleTag);
        LambdaQueryWrapper<MessageSubscribeRuleDO> queryWrapper = getRuleQueryWrapper(ruleDO);
        MessageSubscribeRuleDO subscribeRuleDO = getOne(queryWrapper);
        if (Objects.isNull(subscribeRuleDO)) {
            return null;
        }
        ruleDTO.setDetails(subscribeRuleDO.getRule());
        return ruleDTO;
    }

    @Override
    public void doCreateOrUpdateRuleDetail(MessageSubscribeRuleDetailDTO ruleDetailDTO, String creatorId) {
        String ruleId = ruleDetailDTO.getRuleId();
        LambdaQueryWrapper<MessageSubscribeRuleUserDO> queryWrapper = Wrappers.<MessageSubscribeRuleUserDO>lambdaQuery()
                .eq(MessageSubscribeRuleUserDO::getRuleId, ruleId);
        subscribeRuleUserService.remove(queryWrapper);
        List<String> userIds = ruleDetailDTO.getUserIds();
        if (!CollectionUtils.isEmpty(userIds)) {
            List<MessageSubscribeRuleUserDO> detailDOList = new ArrayList<>();
            Date date = new Date();
            userIds.forEach(userId -> {
                MessageSubscribeRuleUserDO detailDO = new MessageSubscribeRuleUserDO();
                detailDO.setCreatorId(creatorId);
                detailDO.setCreateTime(date);
                detailDO.setRuleId(ruleId);
                detailDO.setUserId(userId);
                detailDOList.add(detailDO);
            });
            subscribeRuleUserService.saveOrUpdateBatch(detailDOList);
        }
    }

    @Override
    public List<String> queryRuleByUserId(String userId) {
        List<MessageSubscribeRuleDO> result = new ArrayList<>();
        LambdaQueryWrapper<MessageSubscribeRuleUserDO> queryWrapper = Wrappers.<MessageSubscribeRuleUserDO>lambdaQuery()
                .eq(MessageSubscribeRuleUserDO::getUserId, userId);
        List<MessageSubscribeRuleUserDO> detailDOList = subscribeRuleUserService.list(queryWrapper);
        // 当存在授权用户
        if (CollectionUtils.isNotEmpty(detailDOList)) {
            List<String> ruleIds = detailDOList.stream().map(MessageSubscribeRuleUserDO::getRuleId).collect(Collectors.toList());
            List<MessageSubscribeRuleDO> subscribeRuleDOList = listByIds(ruleIds);
            if (CollectionUtils.isNotEmpty(subscribeRuleDOList)) {
                result.addAll(subscribeRuleDOList);
            }
        }
        // 查询所有规则，过滤用户
        List<MessageSubscribeRuleDO> allRule = list();
        for (MessageSubscribeRuleDO rule : allRule) {
            // 判断规则是否为强匹配
            if (MessageConstants.YES.equals(rule.getStrongMatch())) {
                if (CollectionUtils.isNotEmpty(detailDOList) && CollectionUtils.isNotEmpty(detailDOList.stream().filter(detail -> detail.getRuleId().equals(rule.getId())).collect(Collectors.toList()))) {
                    result.add(rule);
                }
            } else {
                MessageSubscribeRuleDTO ruleDTO = new MessageSubscribeRuleDTO();
                ruleDTO.setDetails(rule.getRule());
                List<MessageRuleUserDTO> userDTOList = messageRuleUserService.queryAvailableUserList(ruleDTO.getDetails(), userId);
                List<String> userIdList = userDTOList.stream().map(MessageRuleUserDTO::getUserId).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(userIdList) && userIdList.contains(userId)) {
                    result.add(rule);
                }
            }
        }
        if (CollectionUtils.isEmpty(result)) {
            return new ArrayList<>();
        }
        // 将规则拆分返回
        List<String> ruleStrList = new ArrayList<>();
        result.forEach(rule -> {
            MessageSubscribeRuleDTO ruleDTO = new MessageSubscribeRuleDTO();
            ruleDTO.setModuleTag(rule.getModuleTag());
            ruleDTO.setDetails(rule.getRule());
            List<String> rules = wrapperRuleStrList(ruleDTO, userId);
            if (CollectionUtils.isNotEmpty(rules)) {
                ruleStrList.addAll(rules);
            }
        });
        // 去重返回
        return ruleStrList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> queryRuleByRoleId(List<String> ruleIds, List<MessageRuleDetail> ruleDetails) {
        Map<String, List<List<String>>> ruleDetailTypeMap = new HashMap<>(16);
        List<MessageSubscribeRuleDO> messageSubscribeRules = list();
        if (CollectionUtils.isNotEmpty(messageSubscribeRules)) {
            Map<String, List<MessageSubscribeRuleDO>> subscribeRuleMap = messageSubscribeRules.stream().collect(Collectors.groupingBy(MessageSubscribeRuleDO::getModuleTag));
            for (Map.Entry<String, List<MessageSubscribeRuleDO>> entry : subscribeRuleMap.entrySet()) {
                List<List<String>> ruleDetailTypeList = ruleDetailTypeMap.getOrDefault(entry.getKey(), new ArrayList<>());
                List<String> ruleStrList = getUleStrList(ruleIds, entry.getValue());
                if (CollectionUtils.isNotEmpty(ruleStrList)) {
                    ruleDetailTypeList.add(ruleStrList);
                    if (CollectionUtils.isNotEmpty(ruleDetails)) {
                        ruleDetailTypeList.add(ruleDetails.stream().map(i -> String.format(MessageConstants.MESSAGE_SUBSCRIBE_RULE_DETAIL, i.getType(), i.getKey())).collect(Collectors.toList()));
                    }
                    ruleDetailTypeMap.put(entry.getKey(), ruleDetailTypeList);
                }
            }
        }
        List<String> ruleList = new ArrayList<>();
        for (Map.Entry<String, List<List<String>>> entry : ruleDetailTypeMap.entrySet()) {
            ruleList.addAll(descartes(String.format(MessageConstants.MESSAGE_SUBSCRIBE_RULE_TAG, entry.getKey()), entry.getValue()));
        }
        return ruleList;
    }

    @Override
    public List<String> queryRuleByTagAndRoleId(String moduleTag, List<String> ruleIds, List<MessageRuleDetail> ruleDetails) {
        List<List<String>> ruleDetailTypeList = new ArrayList<>();
        List<MessageSubscribeRuleDO> messageSubscribeRules = list(Wrappers.<MessageSubscribeRuleDO>lambdaQuery().eq(MessageSubscribeRuleDO::getModuleTag, moduleTag));
        if (CollectionUtils.isNotEmpty(messageSubscribeRules)) {
            List<String> ruleStrList = new ArrayList<>(getUleStrList(ruleIds, messageSubscribeRules));
            if (CollectionUtils.isNotEmpty(ruleStrList)) {
                ruleDetailTypeList.add(ruleStrList);
                if (CollectionUtils.isNotEmpty(ruleDetails)) {
                    ruleDetailTypeList.add(ruleDetails.stream().map(i -> String.format(MessageConstants.MESSAGE_SUBSCRIBE_RULE_DETAIL, i.getType(), i.getKey())).collect(Collectors.toList()));
                }
            }
        }
        return descartes(String.format(MessageConstants.MESSAGE_SUBSCRIBE_RULE_TAG, moduleTag), ruleDetailTypeList);
    }

    /**
     * 获取UleStrList
     *
     * @param ruleIds               规则id
     * @param messageSubscribeRules 消息订阅规则
     * @return UleStrList
     */
    private List<String> getUleStrList(List<String> ruleIds, List<MessageSubscribeRuleDO> messageSubscribeRules) {
        List<String> ruleStrList = new ArrayList<>();
        for (MessageSubscribeRuleDO messageSubscribeRuleDO : messageSubscribeRules) {
            ruleStrList.addAll(messageSubscribeRuleDO.getRule().stream()
                    .filter(item -> ruleIds.contains(item.getKey()) && MessageConstants.ROLE.equals(item.getType()))
                    .map(i -> String.format(MessageConstants.MESSAGE_SUBSCRIBE_RULE_DETAIL, i.getType(), i.getKey())).collect(Collectors.toList()));
        }
        return ruleStrList;
    }

    @Override
    public void deleteRule(String ruleId) {
        LambdaQueryWrapper<MessageSubscribeRuleUserDO> queryWrapper = Wrappers.<MessageSubscribeRuleUserDO>lambdaQuery()
                .eq(MessageSubscribeRuleUserDO::getRuleId, ruleId);
        subscribeRuleUserService.remove(queryWrapper);
        removeById(ruleId);
    }

    @Override
    public List<String> getRuleByParams(String moduleTag, List<MessageRuleDetail> details) {
        String ruleTag = String.format(MessageConstants.MESSAGE_SUBSCRIBE_RULE_TAG, moduleTag);
        // 不存在规则细项，则该规则不计算在内，直接返回
        if (CollectionUtils.isEmpty(details)) {
            return null;
        }
        // 根据规则详细类型分类
        Map<String, List<String>> detailByTypeList = details.stream().filter(o -> Objects.nonNull(o.getType())).collect(Collectors.groupingBy(MessageRuleDetail::getType, Collectors.mapping(MessageRuleDetail::getKey, Collectors.toList())));
        List<List<String>> ruleDetailTypeList = new ArrayList<>();
        // 获取所有类型的全部ID列表，例如：[["role:1","role:2"],["company:1"]]
        detailByTypeList.forEach((type, ids) -> {
            List<String> ruleDetail = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(ids)) {
                ids.forEach(id -> ruleDetail.add(String.format(MessageConstants.MESSAGE_SUBSCRIBE_RULE_DETAIL, type, id)));
                ruleDetailTypeList.add(ruleDetail);
            }
        });
        // 笛卡尔积，生成规则
        return descartes(ruleTag, ruleDetailTypeList);
    }

    /**
     * 构造查询条件
     *
     * @param ruleDO 入参
     * @return 查询条件
     */
    private LambdaQueryWrapper<MessageSubscribeRuleDO> getRuleQueryWrapper(MessageSubscribeRuleDO ruleDO) {
        return Wrappers.<MessageSubscribeRuleDO>lambdaQuery()
                .eq(StringUtils.isNotEmpty(ruleDO.getModuleTag()), MessageSubscribeRuleDO::getModuleTag, ruleDO.getModuleTag());
    }

    /**
     * 查询规则已经配置的用户列表
     *
     * @param ruleId 规则ID
     * @return 已经配置的用户列表
     */
    private List<MessageRuleUserDTO> queryRuleUsers(String ruleId) {
        LambdaQueryWrapper<MessageSubscribeRuleUserDO> queryWrapper = Wrappers.<MessageSubscribeRuleUserDO>lambdaQuery()
                .eq(MessageSubscribeRuleUserDO::getRuleId, ruleId);
        List<MessageSubscribeRuleUserDO> detailDOList = subscribeRuleUserService.list(queryWrapper);
        List<MessageRuleUserDTO> userList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(detailDOList)) {
            List<String> userIds = detailDOList.stream().map(MessageSubscribeRuleUserDO::getUserId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(userIds)) {
                userList = messageRuleUserService.queryUserByIds(userIds);
            }
        }
        return userList;
    }

    /**
     * 解析规则，生成前端需要订阅的规则列表
     *
     * @param ruleDTO 规则入参
     * @param userId  当前用户ID
     * @return 前端需要订阅的规则列表
     */
    private List<String> wrapperRuleStrList(MessageSubscribeRuleDTO ruleDTO, String userId) {
        String moduleTag = ruleDTO.getModuleTag();
        String ruleTag = String.format(MessageConstants.MESSAGE_SUBSCRIBE_RULE_TAG, moduleTag);
        List<MessageRuleDetail> details = ruleDTO.getDetails();
        // 不存在规则细项，则该规则不计算在内，直接返回
        if (CollectionUtils.isEmpty(details)) {
            return null;
        }
        // 根据规则详细类型分类
        Map<String, List<String>> detailByTypeList = details.stream().filter(o -> Objects.nonNull(o.getType())).collect(Collectors.groupingBy(MessageRuleDetail::getType, Collectors.mapping(MessageRuleDetail::getKey, Collectors.toList())));
        List<List<String>> ruleDetailTypeList = new ArrayList<>();
        // 获取所有类型的全部ID列表，例如：[["role:1","role:2"],["company:1"]]
        detailByTypeList.forEach((type, ids) -> {
            List<String> typeIds = messageRuleUserService.queryDetailListByType(type, userId);
            List<String> ruleDetail = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(ids)) {
                // 取交集
                ids.stream().filter(typeIds::contains).collect(Collectors.toList()).forEach(id -> ruleDetail.add(String.format(MessageConstants.MESSAGE_SUBSCRIBE_RULE_DETAIL, type, id)));
                ruleDetailTypeList.add(ruleDetail);
            } else {
                if (CollectionUtils.isNotEmpty(typeIds)) {
                    typeIds.forEach(id -> ruleDetail.add(String.format(MessageConstants.MESSAGE_SUBSCRIBE_RULE_DETAIL, type, id)));
                    ruleDetailTypeList.add(ruleDetail);
                }
            }
        });
        // 笛卡尔积，生成规则
        return descartes(ruleTag, ruleDetailTypeList);
    }

    /**
     * 列表元素笛卡尔积
     *
     * @param prefix 前缀
     * @param lists  列表
     * @return 结果
     */
    private List<String> descartes(String prefix, List<List<String>> lists) {
        List<String> tempList = new ArrayList<>();
        for (List<String> list : lists) {
            if (tempList.isEmpty()) {
                tempList = list.stream().map(item -> prefix + item).collect(Collectors.toList());
            } else {
                tempList = tempList.stream().flatMap(item -> list.stream().map(item2 -> String.format(MessageConstants.MESSAGE_SUBSCRIBE_RULE_DETAIL_SEPARATOR, item, item2))).collect(Collectors.toList());
            }
        }
        return tempList;
    }

}
