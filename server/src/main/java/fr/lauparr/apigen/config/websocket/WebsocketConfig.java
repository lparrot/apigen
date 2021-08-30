package fr.lauparr.apigen.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(final StompEndpointRegistry registry) {
    registry.addEndpoint("/ws")
      .setAllowedOrigins("*")
      .addInterceptors(this.httpSessionHandshakeInterceptor());
  }

  @Override
  public void configureMessageBroker(final MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic");
  }

  @Bean
  public HandshakeInterceptor httpSessionHandshakeInterceptor() {
    return new HandshakeInterceptor() {
      @Override
      public boolean beforeHandshake(final ServerHttpRequest request, final ServerHttpResponse response, final WebSocketHandler wsHandler, final Map<String, Object> attributes
      ) {
        if (request instanceof ServletServerHttpRequest) {
          final ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
          attributes.put("ipAddress", servletRequest.getRemoteAddress());
        }
        return true;
      }

      @Override
      public void afterHandshake(
        final ServerHttpRequest request,
        final ServerHttpResponse response,
        final WebSocketHandler wsHandler,
        final Exception exception
      ) {
      }
    };
  }
}
