package com.gachi.watching.listener;

import com.gachi.common.config.KafkaConfig;
import com.gachi.watching.event.WatchingSessionChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
    name = "gachi.async.event.type",
    havingValue = "kafka"
)
public class KafkaWatchingEventBroadcastListener {

  private final WatchingEventBroadcastProcessor processor;

  // kafka 리스너
  @KafkaListener(topics = KafkaConfig.WATCHING_SESSION_CHANGED_TOPIC)
  public void onKafkaEvent(WatchingSessionChangedEvent event) {
    processor.on(event);
  }
}
