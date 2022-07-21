package org.message.serviceapi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * <p>
 * 消息阅览历史记录
 * </p>
 *
 * @author WECENG
 * @since 2020/10/16 13:27
 */
@TableName("message_log_read")
@ApiModel(description = "消息阅览历史记录")
@Data
public class MessageLogReadDO {

    /**
     * id;主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户id
     */
    @NotEmpty
    @TableField("user_id")
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 消息唯一码
     */
    @NotEmpty
    @TableField("message_id")
    @ApiModelProperty(value = "消息唯一码")
    private String messageId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
