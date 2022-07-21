package org.message.serviceapi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 消息订阅规则用户配置实体模型
 *
 * @author WECENG
 * @since 2020年10月23日
 **/
@TableName("message_subscribe_rule_user")
@ApiModel(description = "消息订阅规则用户配置表")
@Data
public class MessageSubscribeRuleUserDO {

    /**
     * id;主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 模块类型
     */
    @TableField("rule_id")
    @ApiModelProperty(value = "规则ID")
    private String ruleId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    @ApiModelProperty(value = "用户ID")
    private String userId;

    /**
     * 创建者
     */
    @TableField("creator_id")
    @ApiModelProperty(value = "创建者")
    private String creatorId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 最近一次修改人
     */
    @TableField("updater_id")
    @ApiModelProperty(value = "最近一次修改人")
    private String updaterId;

    /**
     * 最后一次修改时间
     */
    @TableField("update_time")
    @ApiModelProperty(value = "最后一次修改时间")
    private Date updateTime;


}
