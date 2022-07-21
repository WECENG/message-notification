package org.message.client.configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author WECENG
 * @since 2020/10/19 17:50
 */
@ComponentScan(basePackages = {"org.message.client.consumer", "org.message.client.netty"})
@Configuration
@ConditionalOnProperty(prefix = "websocket.server", name = "enable", havingValue = "true")
public class NotificationClientAutoConfig {
}
