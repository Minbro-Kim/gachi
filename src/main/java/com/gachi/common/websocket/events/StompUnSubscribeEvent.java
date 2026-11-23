package com.gachi.common.websocket.events;

public record StompUnSubscribeEvent(
    String sessionId,
    String destination
) {

}
