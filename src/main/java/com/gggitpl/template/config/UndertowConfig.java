package com.gggitpl.template.config;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Undertow 警告
 *
 * @author gggitpl
 */
@Configuration
public class UndertowConfig {

    /**
     * UT026010: Buffer pool was not set on WebSocketDeploymentInfo, the default pool will be used
     *
     * @return WebServerFactoryCustomizer
     */
    @Bean
    public WebServerFactoryCustomizer<UndertowServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
            deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
        });
    }
}
