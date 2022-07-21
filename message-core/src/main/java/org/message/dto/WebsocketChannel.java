package org.message.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 *  websocket连接信息
 * </p>
 *
 * @author WECENG
 * @since 2020/10/18 1:26
 */
@Data
@NoArgsConstructor
public class WebsocketChannel implements Serializable {

    private static final long serialVersionUID = -956539630332036154L;

    /**
     * 主题
     */
    private String topic;

    /**
     * 管道集合
     */
    private Set<String> channelSet;

    /**
     * 时间戳
     */
    private Long timestamp;
}
