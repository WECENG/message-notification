package org.message.serviceapi.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author WECENG
 * @since 2020年10月23日
 *
 **/
public class MessageSubscribeRuleDetailDTO implements Serializable {

    private static final long serialVersionUID = 5882619103822063126L;

    /**
     * 规则ID
     */
    private String ruleId;

    /**
     * 用户ID列表
     */
    private List<String> userIds;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
