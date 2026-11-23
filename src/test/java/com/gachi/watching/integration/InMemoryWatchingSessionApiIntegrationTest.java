package com.gachi.watching.integration;

import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "watching.storage.type=in-memory")
public class InMemoryWatchingSessionApiIntegrationTest extends WatchingSessionApiIntegrationTest {

}
