package me.kuropatva.thatsall.web.socket;

import me.kuropatva.thatsall.web.Auth;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class AuthHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        var a = request.getHeaders().getFirst("cookie").split("=|(; )");
        for (int i = 0; i < a.length; i += 2) {
            attributes.putIfAbsent(a[i], a[i + 1]);
        }
        return Auth.authorize((String) attributes.get("gameID"), (String) attributes.get("username"), (String) attributes.get("passcode"));
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
