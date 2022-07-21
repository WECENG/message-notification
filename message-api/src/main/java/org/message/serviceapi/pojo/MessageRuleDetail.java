package org.message.serviceapi.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author WECENG
 * @since 2020年10月23日
 *
 **/
public class MessageRuleDetail implements Serializable {

    private static final long serialVersionUID = 3375914843348522337L;

    /**
     * 配置项ID，例如用户ID
     */
    @ApiModelProperty("配置项ID，例如用户ID")
    private String key;

    /**
     * 配置项类型，例如角色role、分公司company...
     */
    @ApiModelProperty("配置项类型")
    private String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
