package com.gachi.watching.dto;

import com.gachi.watching.enums.ChangeType;

public record WatchingSessionChange(

    ChangeType type,

    WatchingSessionDto watchingSession,

    long watcherCount
) {

}
