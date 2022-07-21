package org.message.serviceimpl.record.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.message.serviceapi.dto.MessageLogReqDTO;
import org.message.serviceapi.dto.MessageLogRespDTO;
import org.message.serviceapi.enums.MesLogStatusEnum;
import org.message.serviceapi.pojo.MessageLogDO;
import org.message.serviceapi.pojo.MessageLogReadDO;
import org.message.serviceapi.record.MessageLogReadService;
import org.message.serviceapi.record.MessageLogService;
import org.message.serviceimpl.mapper.MessageLogReadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 消息历史记录状态服务实现类
 * </p>
 *
 * @author WECENG
 * @since 2020/10/16 13:46
 */
@Service("messageLogStatusService")
public class MessageLogReadServiceImpl extends ServiceImpl<MessageLogReadMapper, MessageLogReadDO> implements MessageLogReadService {

    @Autowired
    private MessageLogService messageLogService;

    @Override
    public IPage<MessageLogRespDTO> queryList(MessageLogReqDTO messageLogReqDTO, Page<MessageLogDO> page) {
        if (CollectionUtils.isEmpty(messageLogReqDTO.getTopicList())) {
            return new Page<>();
        }
        //read log
        LambdaQueryWrapper<MessageLogReadDO> messageLogReadLambdaQueryWrapper = Wrappers.<MessageLogReadDO>lambdaQuery()
                .eq(!StringUtils.isEmpty(messageLogReqDTO.getUserId()), MessageLogReadDO::getUserId, messageLogReqDTO.getUserId());
        List<MessageLogReadDO> messageLogReadDOList = list(messageLogReadLambdaQueryWrapper);
        List<String> messageIdList = messageLogReadDOList.stream().map(MessageLogReadDO::getMessageId).collect(Collectors.toList());
        if (!StringUtils.isEmpty(messageLogReqDTO.getStatus()) && MesLogStatusEnum.READED.equals(messageLogReqDTO.getStatus()) && CollectionUtils.isEmpty(messageIdList)) {
            return new Page<>();
        }
        Set<String> likeTopicSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(messageLogReqDTO.getTopicList())) {
            // 指定监听，并且指定监听只能在权限内监听
            if (!CollectionUtils.isEmpty(messageLogReqDTO.getLikeTopicList())) {
                messageLogReqDTO.getLikeTopicList().removeIf(value -> !messageLogReqDTO.getTopicList().contains(value));
                likeTopicSet.addAll(CollectionUtils.isEmpty(messageLogReqDTO.getLikeTopicList()) ? Collections.singletonList(StringPool.NULL) : messageLogReqDTO.getLikeTopicList());
            } else {
                likeTopicSet.addAll(messageLogReqDTO.getTopicList());
            }
        } else {
            likeTopicSet.add(StringPool.NULL);
        }
        //message log
        LambdaQueryWrapper<MessageLogDO> queryWrapper = Wrappers.<MessageLogDO>lambdaQuery()
                .ge(!StringUtils.isEmpty(messageLogReqDTO.getBeginTime()), MessageLogDO::getCreateTime, messageLogReqDTO.getBeginTime())
                .le(!StringUtils.isEmpty(messageLogReqDTO.getEndTime()), MessageLogDO::getCreateTime, messageLogReqDTO.getEndTime())
                //unread message
                .notIn(!StringUtils.isEmpty(messageLogReqDTO.getStatus()) && MesLogStatusEnum.UNREAD.equals(messageLogReqDTO.getStatus()) && !CollectionUtils.isEmpty(messageIdList), MessageLogDO::getMessageId, messageIdList)
                //read message
                .in(!StringUtils.isEmpty(messageLogReqDTO.getStatus()) && MesLogStatusEnum.READED.equals(messageLogReqDTO.getStatus()) && !CollectionUtils.isEmpty(messageIdList), MessageLogDO::getMessageId, messageIdList)
                .in(MessageLogDO::getTopic, likeTopicSet);
        // 广播
        queryWrapper.or().eq(Objects.nonNull(messageLogReqDTO.getBroadcast()) && messageLogReqDTO.getBroadcast(), MessageLogDO::getBroadcast, messageLogReqDTO.getBroadcast());
        Page<MessageLogDO> messageLogDoPage = messageLogService.page(page, queryWrapper.orderByDesc(MessageLogDO::getCreateTime));
        return messageLogDoPage.convert(item -> {
            MessageLogRespDTO messageLogRespDTO = new MessageLogRespDTO();
            messageLogRespDTO.setMessageId(item.getMessageId());
            messageLogRespDTO.setPayload(item.getPayload());
            messageLogRespDTO.setBroadcast(item.getBroadcast());
            messageLogRespDTO.setCreateTime(item.getCreateTime());
            messageLogRespDTO.setTopic(item.getTopic());
            MessageLogReadDO messageLogReadDO = messageLogReadDOList.stream().filter(itemMessageLogStatusDO -> item.getMessageId().equals(itemMessageLogStatusDO.getMessageId())).findAny().orElse(null);
            messageLogRespDTO.setStatus(Objects.isNull(messageLogReadDO) ? MesLogStatusEnum.UNREAD : MesLogStatusEnum.READED);
            return messageLogRespDTO;
        });
    }

}
