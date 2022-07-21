package org.message.serviceapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.serviceapi.pojo.MessageRuleDetail;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author WECENG
 * @since 2020年10月23日
 *
 **/
@Data
@NoArgsConstructor
public class MessageSubscribeRuleDTO implements Serializable {

    private static final long serialVersionUID = -9175210575143531482L;

    /**
     * 规则ID
     */
    private String ruleId;

    /**
     * 模块类型
     */
    @NotEmpty
    @ApiModelProperty("模块类型")
    private String moduleTag;

    /**
     * 模块描述
     */
    @ApiModelProperty("模块描述")
    private String moduleDescription;

    /**
     * 规则配置细项
     */
    @ApiModelProperty("规则配置细项")
    private List<MessageRuleDetail> details;

    /**
     * 是否强匹配，若为是，则该消息类型只发送给授权的用户，若为否，则发送给所有有权限的用户
     */
    @ApiModelProperty("是否强匹配，若为是，则该消息类型只发送给授权的用户，若为否，则发送给所有有权限的用户")
    private String strongMatch;

}
