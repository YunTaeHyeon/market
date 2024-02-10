package com.study.market.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtAuthenticationToken implements AuthenticationToken {
    private String accessToken;
    private String refreshToken;
}

/*
public class JwtAuthenticationToken implements AuthenticationToken{

    private String accessToken;
    private String refreshToken;

    @Override
    public String getAccessToken() {
        return null;
    }

    @Override
    public String getRefreshToken() {
        return null;
    }
}
*/