package org.message.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 生产者消息dto
 * </p>
 *
 * @author WECENG
 * @since 2020/7/24 9:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = -6387339209557511563L;

    /**
     * 消息唯一标识
     */
    @ApiModelProperty("消息唯一标识")
    @NotNull
    private String messageId;

    /**
     * 广播发送
     */
    @ApiModelProperty( "广播发送")
    @NotNull
    private Boolean broadcast;

    /**
     * 主题
     */
    @ApiModelProperty(value = "主题")
    private String topic;

    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容")
    @NotNull
    private String payload;

}
