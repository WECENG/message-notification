package org.message.enums;

/**
 * <p>
 * 消息类型
 * </p>
 *
 * @author WECENG
 * @since 2020/7/28 14:32
 */
public enum MesTypeEnum {
    /**
     * 心跳检测
     */
    PINGPONG("心跳"),
    /**
     * 订阅消息
     */
    SUBSCRIBE("订阅"),
    /**
     * 取消订阅
     */
    UNSUBSCRIBE("取消订阅");

    private final String name;

    MesTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
