package com.study.market.member.domain;

import com.study.market.auth.AuthenticationToken;
import lombok.Getter;

@Getter
public class ResponseRefreshTokenDto {
    private final String accessToken;
    private final String refreshToken;

    public ResponseRefreshTokenDto(AuthenticationToken token) {
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
    }
}
