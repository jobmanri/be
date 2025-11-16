package com.example.bak.user.application;

import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.user.application.dto.ProfileResult;
import com.example.bak.user.application.dto.UserResult;
import com.example.bak.user.domain.Profile;
import com.example.bak.user.domain.User;
import com.example.bak.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResult createUser(String email, String password, String name, String nickname) {
        User newUser = User.create(email, password, name, nickname);
        User savedUser = userRepository.save(newUser);

        return UserResult.of(savedUser.getId());
    }

    @Transactional(readOnly = true)
    public ProfileResult getUserProfile(Long userId) {
        Profile profile = userRepository.findProfileByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return ProfileResult.from(profile);
    }
}
