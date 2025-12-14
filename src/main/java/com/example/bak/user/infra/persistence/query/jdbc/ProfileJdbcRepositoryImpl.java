package com.example.bak.user.infra.persistence.query.jdbc;

import com.example.bak.user.domain.Profile;
import com.example.bak.user.domain.User;
import com.example.bak.user.infra.persistence.query.jdbc.mapper.ProfileMapper;
import com.example.bak.user.infra.persistence.query.jdbc.mapper.UserMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProfileJdbcRepositoryImpl implements ProfileJdbcRepository {

    private final NamedParameterJdbcTemplate repository;

    @Override
    public Optional<Profile> findById(Long id) {
        String sql = """
                SELECT  
                    p.id as profile_id,
                    p.name as profile_name,
                    p.nickname as profile_nickname
                FROM profiles p
                WHERE p.id = :id
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        Profile profile = repository.queryForObject(sql, params, new ProfileMapper());
        return Optional.ofNullable(profile);
    }

    @Override
    public Optional<Profile> findByName(String name) {
        String sql = """
                SELECT  
                    p.id as profile_id,
                    p.name as profile_name,
                    p.nickname as profile_nickname
                FROM profiles p
                WHERE p.name = :name
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        Profile profile = repository.queryForObject(sql, params, new ProfileMapper());
        return Optional.ofNullable(profile);
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        String sql = """
                    select 
                        u.id as user_id,
                        u.email as user_email,
                        u.password as user_password
                    from users u
                    left join profiles p on p.user_id = u.id
                    where u.id = :userId
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        User user = repository.queryForObject(sql, params, new UserMapper());
        return Optional.ofNullable(user);
    }
}
