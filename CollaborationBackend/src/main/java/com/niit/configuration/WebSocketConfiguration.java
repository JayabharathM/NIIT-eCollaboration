package com.niit.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //enable broker basedstomp messaging
@ComponentScan(basePackages="com.niit")

public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer 
{

	public void configureClientInboundChannel(ChannelRegistration arg0) {
		
		// TODO Auto-generated method stub
		
	}

	public void configureClientOutboundChannel(ChannelRegistration arg0) {
		// TODO Auto-generated method stub
		
	}

	public void configureMessageBroker(MessageBrokerRegistry registry) {
		System.out.println("CONFIGURE MESSAGE BROKER ");
		// to send messages from spring controller to client
		registry.enableSimpleBroker("/queue/","/topic/");
		//to send messages from client to spring controller
		registry.setApplicationDestinationPrefixes("/app");
		
		
	}

	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// TODO Auto-generated method stub
		System.out.println("Inside REGISTER STOMP END POINTS");
		registry.addEndpoint("/portfolio").withSockJS();
		
	}

}
