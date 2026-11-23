package com.gachi.watching.integration;

import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "watching.storage.type=redis")
public class RedisWatchingSessionApiIntegrationTest extends WatchingSessionApiIntegrationTest {

}
