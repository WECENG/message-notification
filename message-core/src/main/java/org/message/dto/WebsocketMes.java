package org.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.message.enums.MesTypeEnum;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * websocket消息
 * </p>
 *
 * @author WECENG
 * @since 2020/7/28 14:27
 */
@Data
@NoArgsConstructor
public class WebsocketMes implements Serializable {

    private static final long serialVersionUID = 5891930446955694499L;

    /**
     * 消息类型
     */
    @JsonFormat(shape = JsonFormat.Shape.NATURAL)
    private MesTypeEnum mesType;

    /**
     * 主题
     */
    private List<String> topicList;

}
