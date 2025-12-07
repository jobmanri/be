package com.example.bak.user.application.query;

import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.user.application.query.dto.ProfileResult;
import com.example.bak.user.application.query.port.UserQueryPort;
import com.example.bak.user.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserQueryPort userQueryPort;

    @Transactional
    public ProfileResult getUserProfile(Long userId) {
        Profile profile = userQueryPort.findProfileByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return ProfileResult.from(profile);
    }
}
