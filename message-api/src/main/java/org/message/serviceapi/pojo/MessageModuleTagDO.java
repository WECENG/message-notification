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
 * @author WECENG
 *  消息订阅模块类型实体模型
 * @since 2020年10月23日
 **/
@TableName("message_module_tag")
@ApiModel(description = "消息订阅模块类型")
@Data
public class MessageModuleTagDO {

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
     * 描述
     */
    @TableField("description")
    @ApiModelProperty(value = "描述")
    private String description;

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
