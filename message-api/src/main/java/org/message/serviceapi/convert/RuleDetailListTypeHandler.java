package org.message.serviceapi.convert;

import com.alibaba.fastjson.TypeReference;
import org.message.serviceapi.pojo.MessageRuleDetail;

import java.util.List;

/**
 * <p>
 * MessageRuleDetail转换器
 * </p>
 *
 * @author chenwc@tsintergy.com
 * @since 2022/7/21 11:30
 */
public class RuleDetailListTypeHandler extends ListTypeHandler<MessageRuleDetail>{
    @Override
    protected TypeReference<List<MessageRuleDetail>> specificType() {
        return new TypeReference<List<MessageRuleDetail>>(){};
    }
}
