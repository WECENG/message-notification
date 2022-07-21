package org.message.serviceapi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 消息历史记录实体
 * </p>
 *
 * @author WECENG
 * @since 2020/7/29 11:02
 */
@TableName("message_log")
@ApiModel(description = "消息历史记录实体")
@Data
public class MessageLogDO {

    /**
     * id;主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 消息唯一码
     */
    @NotEmpty
    @TableField("message_id")
    @ApiModelProperty(value = "消息唯一码")
    private String messageId;

    /**
     * 消息内容
     */
    @NotEmpty
    @TableField("payload")
    @ApiModelProperty(value = "消息内容")
    private String payload;

    /**
     * 广播消息
     */
    @NotNull
    @TableField("broadcast")
    @ApiModelProperty(value = "广播消息")
    private Boolean broadcast;


    /**
     * 消息主题
     */
    @TableField("topic")
    @ApiModelProperty(value = "消息主题")
    private String topic;

    /**
     * 生成时间
     */
    @TableField("create_time")
    @ApiModelProperty(value = "生成时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
