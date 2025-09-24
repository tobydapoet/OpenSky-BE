package com.example.OpenSky.configs;

import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry metadataSourceRegistry) {
        metadataSourceRegistry
                .simpDestMatchers("/app/send")
                .authenticated()
                .simpSubscribeDestMatchers("/user/queue/**")
                .authenticated()
                .anyMessage()
                .authenticated();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
