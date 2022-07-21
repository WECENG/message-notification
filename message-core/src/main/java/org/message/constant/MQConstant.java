package org.message.constant;

/**
 * <p>
 * 消息通知常量类
 * </p>
 *
 * @author WECENG
 * @since 2020/10/14 21:15
 */
public class MQConstant {

    /**
     * 消息交换机
     */
    public static final String DEFAULT_EXCHANGE = "bossMqExchange";

    /**
     * 路由
     */
    public static final String DEFAULT_ROUTING_KEY = "bossMqRoutingKey";

    /**
     * 队列
     */
    public static final String DEFAULT_QUEUE = "bossMqQueue";

    /**
     * 消息头属性
     */
    public static final String HEADER_BROADCAST = "broadcast";

    /**
     * 消息头属性
     */
    public static final String TOPIC = "topic";

}
