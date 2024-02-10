package com.study.market.auth;

public interface AuthenticationToken {
    String getAccessToken();
    String getRefreshToken();
}
