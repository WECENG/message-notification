package org.message.serviceimpl.rule.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.message.serviceapi.pojo.MessageSubscribeRuleUserDO;
import org.message.serviceapi.rule.MessageSubscribeRuleUserService;
import org.message.serviceimpl.mapper.MessageSubscribeRuleUserMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author WECENG
 * @since 2022/7/19 15:05
 */
@Service
public class MessageSubscribeRuleUserServiceImpl extends ServiceImpl<MessageSubscribeRuleUserMapper, MessageSubscribeRuleUserDO> implements MessageSubscribeRuleUserService {
}
