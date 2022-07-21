package org.message.demo.web.serviceimpl.rule.impl;

import org.message.demo.web.constant.CommonConstants;
import org.message.serviceapi.dto.MessageRuleUserDTO;
import org.message.serviceapi.pojo.MessageRuleDetail;
import org.message.serviceapi.rule.MessageRuleUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WECENG
 * @since 2020年12月31日
 *
 **/
@Service
public class MessageRuleUserServiceImpl implements MessageRuleUserService {

    @Override
    public List<MessageRuleUserDTO> queryAvailableUserList(List<MessageRuleDetail> details, String userId) {
        if (!CollectionUtils.isEmpty(details)) {
            List<MessageRuleUserDTO> ruleUserDTOList = new ArrayList<>();
            Map<String, List<String>> idsByType = details.stream().collect(Collectors.groupingBy(MessageRuleDetail::getType, Collectors.mapping(MessageRuleDetail::getKey, Collectors.toList())));
            idsByType.forEach((type, ids) -> {
                // 根据type和id列表，查询用户列表
                switch (type) {
                    // 角色
                    case CommonConstants.ROLE:
                        ruleUserDTOList.addAll(queryUserByRoleIds(ids));
                        break;
                    // 分公司
                    case CommonConstants.COMPANY:
                        ruleUserDTOList.addAll(queryUserByCompanyIds(ids));
                        break;
                    default:
                        break;
                }
            });
            if (!CollectionUtils.isEmpty(ruleUserDTOList)) {
                // 根据userId去重
                return ruleUserDTOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(MessageRuleUserDTO::getUserId))), ArrayList::new));
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<MessageRuleUserDTO> queryUserByIds(List<String> userIds) {
        List<MessageRuleUserDTO> userDTOList = new ArrayList<>();
        MessageRuleUserDTO zhangSan = new MessageRuleUserDTO("1", "张三");
        userDTOList.add(zhangSan);
        MessageRuleUserDTO liSi = new MessageRuleUserDTO("2", "李四");
        userDTOList.add(liSi);
        MessageRuleUserDTO liQing = new MessageRuleUserDTO("999", "李青");
        userDTOList.add(liQing);
        return userDTOList;
    }

    @Override
    public List<String> queryDetailListByType(String type, String userId) {
        // TODO 根据规则详细类型和用户ID查询该类型所包含的列表（例如type为分公司，则根据用户查询分公司ID列表）
        List<String> keys = new ArrayList<>();
        switch (type) {
            // 角色
            case CommonConstants.ROLE:
                keys.add("admin");
                break;
            // 分公司
            case CommonConstants.COMPANY:
                keys.add("tsie");
                keys.add("alibaba");
                break;
            default:
                break;
        }
        return keys;
    }

    private List<MessageRuleUserDTO> queryUserByRoleIds(List<String> ids){
        // TODO 根据角色ID列表查询包含的用户列表，并转为MessageRuleUserDTO
        List<MessageRuleUserDTO> userDTOList = new ArrayList<>();
        MessageRuleUserDTO admin = new MessageRuleUserDTO("admin", "管理员");
        userDTOList.add(admin);
        MessageRuleUserDTO zhangSan = new MessageRuleUserDTO("1", "张三");
        userDTOList.add(zhangSan);
        MessageRuleUserDTO liSi = new MessageRuleUserDTO("2", "李四");
        userDTOList.add(liSi);
        MessageRuleUserDTO liQing = new MessageRuleUserDTO("999", "李青");
        userDTOList.add(liQing);
        return userDTOList;
    }


    private List<MessageRuleUserDTO> queryUserByCompanyIds(List<String> ids){
        // TODO 根据分公司ID列表查询包含的用户列表，并转为MessageRuleUserDTO
        List<MessageRuleUserDTO> userDTOList = new ArrayList<>();
        MessageRuleUserDTO wangWu = new MessageRuleUserDTO("3", "王五");
        userDTOList.add(wangWu);
        MessageRuleUserDTO zhaoLiu = new MessageRuleUserDTO("4", "赵六");
        userDTOList.add(zhaoLiu);
        MessageRuleUserDTO liSi = new MessageRuleUserDTO("2", "李四");
        userDTOList.add(liSi);
        MessageRuleUserDTO liQing = new MessageRuleUserDTO("999", "李青");
        userDTOList.add(liQing);
        return userDTOList;
    }
}
