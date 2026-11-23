package com.gachi.config.support;

import com.gachi.common.config.QueryDslConfig;
import com.gachi.config.TestcontainersConfig;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import({TestcontainersConfig.class, QueryDslConfig.class})
@DataJpaTest
@ActiveProfiles("test")
@Tag("repository")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTestSupport {

}
