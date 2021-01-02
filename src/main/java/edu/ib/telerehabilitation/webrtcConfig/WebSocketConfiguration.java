//package edu.ib.telerehabilitation.webrtcConfig;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.logging.SocketHandler;
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfiguration implements WebSocketConfigurer {
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//
//            registry.addHandler(new SocketHandler(), "/socket") //SocketHandler - message handler
//                    .setAllowedOrigins("*");
//
//    }
//}
