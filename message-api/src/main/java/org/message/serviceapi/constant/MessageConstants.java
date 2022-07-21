package org.message.serviceapi.constant;

/**
 * @author WECENG
 * @since 2020年11月16日
 **/
public class MessageConstants {

    /**
     * 是
     */
    public static final String YES = "1";

    /**
     * 消息订阅规则分割字符
     */
    public static final String MESSAGE_RULE_SEPARATOR = "#";

    /**
     * 消息订阅规则所属类型分割字符
     */
    public static final String MESSAGE_RULE_BELONG_SEPARATOR = ":";

    /**
     * 消息订阅规则Format串-消息类型：消息模块类型#
     */
    public static final String MESSAGE_SUBSCRIBE_RULE_TAG = "%s" + MESSAGE_RULE_SEPARATOR;

    /**
     * 消息订阅规则Format串-规则细项：type:id
     */
    public static final String MESSAGE_SUBSCRIBE_RULE_DETAIL = "%s" + MESSAGE_RULE_BELONG_SEPARATOR + "%s";

    /**
     * 消息订阅规则Format串-规则细项：detail1#detail2
     */
    public static final String MESSAGE_SUBSCRIBE_RULE_DETAIL_SEPARATOR = "%s" + MESSAGE_RULE_SEPARATOR + "%s";

    /**
     * 规则匹配类型 - 角色
     */
    public static final String ROLE = "role";

    /**
     * 规则匹配类型 - 分公司
     */
    public static final String COMPANY = "company";

}
