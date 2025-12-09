package com.example.bak.global;

import com.example.bak.feed.infra.query.jdbc.FeedJdbcRepository;
import com.example.bak.feed.infra.query.jdbc.FeedJdbcRepositoryImpl;
import com.example.bak.privatemessage.infra.query.jdbc.MessageJdbcRepository;
import com.example.bak.privatemessage.infra.query.jdbc.MessageJdbcRepositoryImpl;
import com.example.bak.user.infra.persistence.query.jdbc.UserJdbcRepository;
import com.example.bak.user.infra.persistence.query.jdbc.UserJdbcRepositoryImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@TestConfiguration
public class JdbcRepositoryTestConfig {

    @Bean
    public MessageJdbcRepository messageJdbcRepository(NamedParameterJdbcTemplate jdbc) {
        return new MessageJdbcRepositoryImpl(jdbc);
    }

    @Bean
    public FeedJdbcRepository feedJdbcRepository(NamedParameterJdbcTemplate jdbc) {
        return new FeedJdbcRepositoryImpl(jdbc);
    }

    @Bean
    public UserJdbcRepository userJdbcRepository(NamedParameterJdbcTemplate jdbc) {
        return new UserJdbcRepositoryImpl(jdbc);
    }
}
