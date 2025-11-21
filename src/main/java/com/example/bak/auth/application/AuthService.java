package com.example.bak.auth.application;

import com.example.bak.auth.application.dto.TokenInfo;
import com.example.bak.auth.infra.jwt.persistence.TokenType;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.user.domain.User;
import com.example.bak.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public TokenInfo login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.matchPassword(password);

        String accessToken = jwtService.publishToken(TokenType.ACCESS, user.getId(), user.getRole());
        String refreshToken = jwtService.publishToken(TokenType.REFRESH, user.getId(), user.getRole());
        return TokenInfo.of(accessToken, refreshToken);
    }
}
