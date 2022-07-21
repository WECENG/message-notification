package org.message.serviceapi.enums;

/**
 * <p>
 * 消息状态
 * </p>
 *
 * @author WECENG
 * @since 2020/7/29 14:13
 */
public enum MesLogStatusEnum {

    /**
     * 已发未读
     */
    UNREAD("未读"),

    /**
     * 已读消息
     */
    READED("已读");


    private final String text;

    MesLogStatusEnum(String text) {
        this.text = text;
    }


    public String getText() {
        return text;
    }
}
