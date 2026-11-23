package com.gachi.watching.event;

import com.gachi.common.config.KafkaConfig;
import com.gachi.common.kafka.ModuplyAsyncEvent;
import com.gachi.watching.enums.ChangeType;
import com.gachi.watching.model.WatchingSession;

public record WatchingSessionChangedEvent(
    ChangeType type,
    WatchingSession watchingSession,
    long watcherCount

) implements ModuplyAsyncEvent {

  @Override
  public String getTopic() {
    return KafkaConfig.WATCHING_SESSION_CHANGED_TOPIC;
  }

  @Override
  public String getPartitionKey() {
    return this.watchingSession.getContentId().toString();
  }
}