package com.example.bak.auth.application.command;

import static com.example.bak.global.exception.ErrorCode.USER_NOT_FOUND;

import com.example.bak.auth.application.command.dto.TokenResult;
import com.example.bak.auth.application.command.port.AuthCommandPort;
import com.example.bak.auth.application.command.port.JwtTokenCommandPort;
import com.example.bak.auth.domain.TokenType;
import com.example.bak.auth.infra.passwordencoder.PasswordEncoderProvider;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthCommandService {

    private final AuthCommandPort authCommandPort;
    private final JwtTokenCommandPort jwtTokenCommandPort;
    private final PasswordEncoderProvider passwordEncoderProvider;

    @Transactional
    public void registerService(String email, String password) {
        String encodedPassword = passwordEncoderProvider.encryptPassword(password);
        authCommandPort.register(email, encodedPassword);
    }

    @Transactional
    public TokenResult login(String email, String password) {
        User user = authCommandPort.findByEmail(email).orElseThrow(() -> new BusinessException(
                USER_NOT_FOUND));

        passwordEncoderProvider.matchPassword(password, user.getPassword());

        String accessToken = jwtTokenCommandPort.publishToken(TokenType.ACCESS, user.getId(),
                user.getRole());
        String refreshToken = jwtTokenCommandPort.publishToken(TokenType.REFRESH, user.getId(),
                user.getRole());

        return TokenResult.of(accessToken, refreshToken);
    }
}
