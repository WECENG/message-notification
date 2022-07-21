package org.message.serviceimpl.record.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.message.serviceapi.pojo.MessageLogDO;
import org.message.serviceapi.record.MessageLogService;
import org.message.serviceimpl.mapper.MessageLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * 消息历史记录服务实现类
 * </p>
 *
 * @author WECENG
 * @since 2020/7/29 11:45
 */
@Service("messageLogService")
public class MessageLogServiceImpl extends ServiceImpl<MessageLogMapper, MessageLogDO> implements MessageLogService {

}
