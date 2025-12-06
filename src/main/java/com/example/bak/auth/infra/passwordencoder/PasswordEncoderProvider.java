package com.example.bak.auth.infra.passwordencoder;

import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncoderProvider {

    private final PasswordEncoder passwordEncoder;

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void matchPassword(String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new BusinessException(ErrorCode.INCORRECT_PASSWORD);
        }
    }
}
