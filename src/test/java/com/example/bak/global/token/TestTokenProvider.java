package com.example.bak.global.token;

import com.example.bak.auth.domain.AccessToken;
import com.example.bak.auth.domain.RefreshToken;
import com.example.bak.auth.infra.jwt.JwtTokenProvider;
import java.util.Base64;
import java.util.List;

public class TestTokenProvider {

    static final String accessKey = "e579b877751a55f861874a50476eed4e6765b7490a6f4b94fc870254f71216eb";
    static final String accessEncodeKey = Base64.getEncoder().encodeToString(accessKey.getBytes());
    static final Long accessTTL = 3600L;

    static final String refreshKey = "9ddb97e624d2ac2667fd95a2c89fde1f8ad42e5ebb1836c6977b00bc82cde83d";
    static final String refreshEncodeKey = Base64.getEncoder()
            .encodeToString(refreshKey.getBytes());
    static final Long refreshTTL = 36000L;

    public static JwtTokenProvider getProvider() {

        AccessToken accessToken = new AccessToken();
        accessToken.setSecretKey(accessEncodeKey);
        accessToken.setTimeToLive(accessTTL);

        accessToken.validate();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setSecretKey(refreshEncodeKey);
        refreshToken.setTimeToLive(refreshTTL);

        refreshToken.validate();

        return new JwtTokenProvider(List.of(accessToken, refreshToken));
    }
}
