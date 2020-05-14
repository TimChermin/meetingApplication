package com.meetingapp.meetingapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        /*I think we can leave this as is regardless, enableSimpleBroker basically sets up the delivery of messages
        to all users on the destination /topic
         */
        config.enableSimpleBroker("/topic");
        //I think setApplicationDestinationPrefix does the same the other way 'round, /app is the prefix for messages back to the server
        //If you'd want to send a message to something to an endpoint named "test" you'd send it to /app/test
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //this sets up all the endpoints basically, pretty self-explanatory
        registry.addEndpoint("/chat");
        registry.addEndpoint("/chat").withSockJS();
    }
}
