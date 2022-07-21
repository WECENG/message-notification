package org.message.web.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import org.message.serviceapi.pojo.MessageModuleTagDO;

/**
 * <p>
 * 消息订阅模块类型-基础信息分页查询请求对象
 * </p>
 *
 * @author wangjx@tsintergy.com
 * @since 2020/10/23 14:42
 */
public class MessageModuleTagPageRequest extends Page<MessageModuleTagDO> {

    /**
     * 模块类型
     */
    @ApiModelProperty(value = "模块类型")
    private String moduleTag;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    public String getModuleTag() {
        return moduleTag;
    }

    public void setModuleTag(String moduleTag) {
        this.moduleTag = moduleTag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
