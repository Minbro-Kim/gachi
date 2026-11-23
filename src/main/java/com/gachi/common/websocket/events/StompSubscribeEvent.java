package com.gachi.common.websocket.events;


import java.util.UUID;

public record StompSubscribeEvent(
    String sessionId,
    UUID userId,
    String destination
) {

}
