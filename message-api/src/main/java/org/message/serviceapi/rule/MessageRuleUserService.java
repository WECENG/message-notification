package org.message.serviceapi.rule;

import org.message.serviceapi.dto.MessageRuleUserDTO;
import org.message.serviceapi.pojo.MessageRuleDetail;

import java.util.List;

/**
 * @author WECENG
 * @since 2020年12月29日
 *
 **/
public interface MessageRuleUserService{

    /**
     * 查询可选用户列表
     *
     * @param details 规则细项
     * @param userId  当前用户ID
     * @return 可选用户列表
     */
    List<MessageRuleUserDTO> queryAvailableUserList(List<MessageRuleDetail> details, String userId);

    /**
     * 根据用户ID列表查询用户列表
     *
     * @param userIds 用户ID列表
     * @return 用户列表
     */
    List<MessageRuleUserDTO> queryUserByIds(List<String> userIds);

    /**
     * 根据规则详细类型和用户ID查询该类型所包含的列表（例如type为分公司，则根据用户查询分公司ID列表）
     *
     * @param type   规则详细类型
     * @param userId 用户ID
     * @return 结果列表
     */
    List<String> queryDetailListByType(String type, String userId);

}
