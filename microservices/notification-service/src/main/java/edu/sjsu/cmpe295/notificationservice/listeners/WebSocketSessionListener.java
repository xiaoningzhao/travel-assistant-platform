package edu.sjsu.cmpe295.notificationservice.listeners;

import edu.sjsu.cmpe295.notificationservice.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WebSocketSessionListener {
    private final NotificationService notificationService;

    @EventListener
    public void connectionEstablished(SessionConnectedEvent sce)
    {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap((Message<?>) sce.getMessage().getHeaders().get("simpConnectMessage"));
        List<String> nativeHeaders = sha.getNativeHeader("userId");
        String sessionId = sha.getSessionId();
        if( nativeHeaders != null )
        {
            String userId = nativeHeaders.get(0);
            notificationService.addConnectedUser(userId, sessionId);
        }

    }

    @EventListener
    public void subscribeEstablished(SessionSubscribeEvent sce)
    {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(sce.getMessage());
        String destination = sha.getNativeHeader("destination").get(0);
        String topic = destination.split("/")[1];
        if(topic.equals("user")) {
            String userId = destination.split("/")[2];
            notificationService.sendMissedNotificationToUser(userId);
        }
    }

    @EventListener
    public void webSocketDisconnect(SessionDisconnectEvent sde)
    {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(sde.getMessage());
        String sessionId = sha.getSessionId();
        notificationService.removeConnectedUser(sessionId);

    }

}
