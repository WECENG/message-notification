package org.message.serviceapi.rule;

import com.baomidou.mybatisplus.extension.service.IService;
import org.message.serviceapi.dto.MessageSubscribeRuleDTO;
import org.message.serviceapi.dto.MessageSubscribeRuleDetailDTO;
import org.message.serviceapi.pojo.MessageRuleDetail;
import org.message.serviceapi.pojo.MessageSubscribeRuleDO;

import java.util.List;

/**
 * @author WECENG
 *
 * @since 2020年10月23日
 **/
public interface MessageSubscribeRuleService extends IService<MessageSubscribeRuleDO> {

    /**
     * 新增规则
     *
     * @param ruleDTO 规则
     * @param userId  用户ID
     * @return 结果
     */
    MessageSubscribeRuleDO doCreateRule(MessageSubscribeRuleDTO ruleDTO, String userId);

    /**
     * 更新规则
     *
     * @param ruleDTO 规则
     * @param userId  用户ID
     * @return 结果
     */
    MessageSubscribeRuleDO doUpdateRule(MessageSubscribeRuleDTO ruleDTO, String userId);

    /**
     * 根据模块类型查询规则
     *
     * @param moduleTag 模块类型
     * @return 规则
     */
    MessageSubscribeRuleDTO queryRuleByModuleTag(String moduleTag);

    /**
     * 新增、编辑规则人员配置
     *
     * @param ruleDetailDTO 规则人员配置
     * @param creatorId     创建用户ID
     */
    void doCreateOrUpdateRuleDetail(MessageSubscribeRuleDetailDTO ruleDetailDTO, String creatorId);

    /**
     * 根据用户查询规则列表
     *
     * @param userId 用户ID
     * @return 规则列表
     */
    List<String> queryRuleByUserId(String userId);

    /**
     * 根据角色id查询主题
     *
     * @param ruleIds     角色id数组
     * @param ruleDetails 规则数组，填组织架构id
     * @return 规则
     */
    List<String> queryRuleByRoleId(List<String> ruleIds, List<MessageRuleDetail> ruleDetails);

    /**
     * 根据标签和角色id查询主题，用于发送消息（ruleDetails存在的话，只针对某个人可以接收）
     *
     * @param moduleTag   标签
     * @param ruleIds     角色id数组
     * @param ruleDetails 规则数组，填组织架构id
     * @return 规则
     */
    List<String> queryRuleByTagAndRoleId(String moduleTag, List<String> ruleIds, List<MessageRuleDetail> ruleDetails);

    /**
     * 根据规则ID删除规则
     *
     * @param ruleId 规则ID
     */
    void deleteRule(String ruleId);

    /**
     * 根据入参封装规则（主题），用于发送消息
     *
     * @param moduleTag 模块类型
     * @param details   规则详细配置
     * @return 规则（消息主题）列表
     */
    List<String> getRuleByParams(String moduleTag, List<MessageRuleDetail> details);

}
