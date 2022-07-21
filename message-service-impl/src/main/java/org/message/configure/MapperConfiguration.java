package org.message.configure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author WECENG
 * @since 2022/7/19 14:58
 */
@Configuration
@MapperScan(basePackages = "org.message.serviceimpl.mapper")
public class MapperConfiguration {
}
