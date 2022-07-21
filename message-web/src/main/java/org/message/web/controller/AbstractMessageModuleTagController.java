package org.message.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.message.serviceapi.pojo.MessageModuleTagDO;
import org.message.serviceapi.rule.MessageModuleTagService;
import org.message.web.request.MessageModuleTagPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author WECENG
 *
 * @since 2020年10月26日
 **/
public abstract class AbstractMessageModuleTagController extends AbstractCurrentUserController {

    @Autowired(required = false)
    private MessageModuleTagService moduleTagService;

    @PostMapping
    @ApiOperation(value = "新增或修改模块类型")
    public String doCreateOrUpdateRule(@RequestBody @Valid MessageModuleTagDO moduleTagDO) {
        String userId = getCurrentLoginUser();
        String tagId = moduleTagDO.getId();
        if (StringUtils.isNotEmpty(tagId)) {
            moduleTagDO.setUpdaterId(userId);
            moduleTagDO.setUpdateTime(new Date());
            MessageModuleTagDO messageModuleTagDO = moduleTagService.doUpdate(moduleTagDO);
            tagId = messageModuleTagDO.getId();
        } else {
            moduleTagDO.setCreatorId(userId);
            moduleTagDO.setCreateTime(new Date());
            MessageModuleTagDO messageModuleTagDO = moduleTagService.doCreate(moduleTagDO);
            tagId = messageModuleTagDO.getId();
        }
        return tagId;
    }

    @GetMapping("/page")
    @ApiOperation(value = "列表查询")
    public IPage<MessageModuleTagDO> queryByPage(MessageModuleTagPageRequest pageRequest) {
        MessageModuleTagDO moduleTagDO = new MessageModuleTagDO();
        moduleTagDO.setModuleTag(pageRequest.getModuleTag());
        return moduleTagService.page(moduleTagDO, pageRequest);
    }

    @GetMapping("/list")
    @ApiOperation(value = "列表查询")
    public List<MessageModuleTagDO> queryList(MessageModuleTagDO moduleTagDO) {
        return moduleTagService.queryList(moduleTagDO);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除规则")
    public void deleteRule(@PathVariable("id") String id) {
        moduleTagService.delete(id);
    }

}
