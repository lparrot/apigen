package fr.lauparr.apigen.config.websocket;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Slf4j
@Service
public class WebSocketEventListener {

  @Getter
  private final SimpMessageSendingOperations messagingTemplate;

  public WebSocketEventListener(final SimpMessageSendingOperations messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  @EventListener
  public void handleWebSocketConnectListener(final SessionConnectedEvent event) {
    //
  }

  @EventListener
  public void handleWebSocketDisconnectListener(final SessionDisconnectEvent event) {
    //
  }
}
