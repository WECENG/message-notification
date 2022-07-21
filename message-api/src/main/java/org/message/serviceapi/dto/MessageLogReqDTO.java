package org.message.serviceapi.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.message.serviceapi.enums.MesLogStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import org.message.serviceapi.pojo.MessageLogDO;
import org.message.serviceapi.pojo.MessageLogReadDO;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 消息历史记录dto
 * </p>
 *
 * @author WECENG
 * @since 2020/10/18 15:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageLogReqDTO extends Page<MessageLogDO> {

    private static final long serialVersionUID = -4905448451045654763L;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String userId;

    /**
     * 主题
     */
    @ApiModelProperty("主题")
    private List<String> topicList;

    /**
     * 消息主题筛选关键字
     */
    @ApiModelProperty("消息主题筛选关键字")
    private List<String> likeTopicList;

    /**
     * 消息唯一码
     */
    @ApiModelProperty("消息唯一码")
    private String messageId;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private MesLogStatusEnum status;

    /**
     * 广播发送
     */
    @ApiModelProperty("广播发送")
    private Boolean broadcast;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
