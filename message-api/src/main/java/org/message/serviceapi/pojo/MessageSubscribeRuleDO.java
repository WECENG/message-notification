package org.message.serviceapi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.message.serviceapi.convert.RuleDetailListTypeHandler;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * @author WECENG
 * @since 2020年10月23日
 * 消息订阅规则实体模型
 **/
@TableName(value = "message_subscribe_rule", autoResultMap = true)
@ApiModel(description = "消息订阅规则")
@Data
public class MessageSubscribeRuleDO {

    /**
     * id;主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 模块类型
     */
    @NotEmpty
    @TableField("module_tag")
    @ApiModelProperty(value = "模块类型")
    private String moduleTag;

    /**
     * 规则
     */
    @TableField(value = "rule",typeHandler = RuleDetailListTypeHandler.class)
    @ApiModelProperty(value = "规则")
    private List<MessageRuleDetail> rule;

    /**
     * 是否强匹配，若为是，则该消息类型只发送给授权的用户，若为否，则发送给所有有权限的用户
     */
    @TableField("strong_match")
    @ApiModelProperty(value = "是否强匹配")
    private String strongMatch;

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
