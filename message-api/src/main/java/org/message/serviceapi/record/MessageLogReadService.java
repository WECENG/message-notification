package org.message.serviceapi.record;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.message.serviceapi.dto.MessageLogReqDTO;
import org.message.serviceapi.dto.MessageLogRespDTO;
import org.message.serviceapi.pojo.MessageLogDO;
import org.message.serviceapi.pojo.MessageLogReadDO;

/**
 * <p>
 * 消息阅览历史记录服务接口
 * </p>
 *
 * @author WECENG
 * @since 2020/10/16 13:43
 */
public interface MessageLogReadService extends IService<MessageLogReadDO> {

    /**
     * 消息历史记录分页查询
     *
     * @param pageable 分页对象
     * @param messageLogReqDTO 请求对象
     * @return
     */
    IPage<MessageLogRespDTO> queryList(MessageLogReqDTO messageLogReqDTO, Page<MessageLogDO> page);


}
