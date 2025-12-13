package com.example.bak.user.infra.persistence.query.jdbc;

import com.example.bak.user.application.query.dto.UserResult;
import com.example.bak.user.domain.Profile;
import com.example.bak.user.domain.User;
import com.example.bak.user.infra.persistence.query.jdbc.mapper.ProfileMapper;
import com.example.bak.user.infra.persistence.query.jdbc.mapper.UserMapper;
import com.example.bak.user.infra.persistence.query.jdbc.mapper.UserResultMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserJdbcRepositoryImpl implements UserJdbcRepository {

    private final NamedParameterJdbcTemplate repository;

    @Override
    public Optional<User> findById(Long userId) {
        String sql = """
                    select
                        u.id as user_id,
                        u.email as user_email,
                        u.password as user_password
                    from users u
                    where u.id = :userId
                """;
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
        List<User> result = repository.query(sql, params, new UserMapper());
        return result.stream().findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = """
                    select
                        u.id as user_id,
                        u.email as user_email,
                        u.password as user_password
                    from users u
                    where u.email = :email
                """;
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("email", email);
        List<User> result = repository.query(sql, params, new UserMapper());
        return result.stream().findFirst();
    }

    @Override
    public Optional<Profile> findProfileByUserId(Long userId) {
        String sql = """
                    select 
                        p.id as profile_id,
                        p.name as profile_name,
                        p.nickname as profile_nickname
                    from profiles p
                    left join users u on u.id = p.user_id
                    where u.id = :userId
                """;
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
        List<Profile> result = repository.query(sql, params, new ProfileMapper());
        return result.stream().findFirst();
    }

    @Override
    public Optional<UserResult> getUserInfoById(Long userId) {
        String sql = """
                    select
                        u.id as user_id,
                        p.nickname as nickname
                    from users u
                    left join profiles p on p.user_id = u.id
                    where u.id = :userId
                """;
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
        List<UserResult> result = repository.query(sql, params, new UserResultMapper());
        return result.stream().findFirst();
    }
}
