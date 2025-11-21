package com.example.bak.global;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcRepositoryTestConfig.class)
public abstract class AbstractMySqlContainerTest {

    private static final MySQLContainer<?> MYSQL =
            new MySQLContainer<>("mysql:8.0.36")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test")
                    .withInitScripts("sql/schema.sql");

    static {
        MYSQL.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
        registry.add("spring.datasource.driver-class-name", MYSQL::getDriverClassName);
    }
}