package ws;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.ArrayList;
import java.util.List;

@ServerEndpoint("/example")
public class WsExample {

    private static final List<Session> SESSIONS = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        long tenMinutes = 1000 * 60 * 10;

        session.setMaxIdleTimeout(tenMinutes);
        SESSIONS.add(session);
        notifyAll("New client connected. Id: " + session.getId());
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        SESSIONS.removeIf(s -> s.getId().equals(session.getId()));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // handle errors
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // handle message from JS client
    }

    public static void notifyAll(String message) {
        for (Session session : SESSIONS) {
            sendMessage(message, session);
        }
    }

    private static void sendMessage(String message, Session session) {
        try {
            session.getBasicRemote().sendObject(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
