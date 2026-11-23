package com.gachi.watching.model;

public record WatchingSessionResult(
    WatchingSession watchingSession,
    long watcherCount
) {

}
