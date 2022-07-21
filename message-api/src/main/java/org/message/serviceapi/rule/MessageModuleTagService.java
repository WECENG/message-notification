package org.message.serviceapi.rule;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.message.serviceapi.pojo.MessageModuleTagDO;

import java.util.List;

/**
 * @author WECENG
 *
 * @since 2020年10月23日
 **/
public interface MessageModuleTagService extends IService<MessageModuleTagDO> {

    /**
     * 新增模块类型
     *
     * @param moduleTagDO 模块类型
     * @return 结果
     */
    MessageModuleTagDO doCreate(MessageModuleTagDO moduleTagDO);

    /**
     * 更新模块类型
     *
     * @param moduleTagDO 模块类型
     * @return 结果
     */
    MessageModuleTagDO doUpdate(MessageModuleTagDO moduleTagDO);

    /**
     * 分页查询
     *
     * @param moduleTagDO 条件
     * @param page        分页对象
     * @return 分页列表
     */
    IPage<MessageModuleTagDO> page(MessageModuleTagDO moduleTagDO, Page<MessageModuleTagDO> page);

    /**
     * 列表查询
     * @param moduleTagDO 条件
     * @return 列表
     */
    List<MessageModuleTagDO> queryList(MessageModuleTagDO moduleTagDO);

    /**
     * 删除
     *
     * @param id 主键
     */
    void delete(String id);

    /**
     * 根据模块类型查询
     *
     * @param moduleTag 模块类型
     * @return 模块类型实体
     */
    MessageModuleTagDO queryByModuleTag(String moduleTag);

}
