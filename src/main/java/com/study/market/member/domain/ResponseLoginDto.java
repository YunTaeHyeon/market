package com.study.market.member.domain;

import com.study.market.auth.AuthenticationToken;
import lombok.Getter;

@Getter
public class ResponseLoginDto {

    private final String accessToken;
    private final String refreshToken;

    public ResponseLoginDto(AuthenticationToken token) {
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
    }
}
