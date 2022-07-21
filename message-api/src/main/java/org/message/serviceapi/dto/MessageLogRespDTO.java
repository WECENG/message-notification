package org.message.serviceapi.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.serviceapi.enums.MesLogStatusEnum;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 消息响应dto
 * </p>
 *
 * @author WECENG
 * @since 2020/10/16 13:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageLogRespDTO implements Serializable {

    private static final long serialVersionUID = 4936123405092989255L;

    /**
     * 消息唯一码
     */
    @ApiModelProperty("消息唯一码")
    private String messageId;

    /**
     * 主题
     */
    @ApiModelProperty("主题")
    private String topic;

    /**
     * 广播消息
     */
    @ApiModelProperty("广播消息")
    private Boolean broadcast;

    /**
     * 消息内容
     */
    @ApiModelProperty("消息内容")
    private String payload;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private MesLogStatusEnum status;

    /**
     * 消息创建时间
     */
    @ApiModelProperty("消息创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
