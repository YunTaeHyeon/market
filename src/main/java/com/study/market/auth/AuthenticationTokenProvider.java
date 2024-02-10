package com.study.market.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface AuthenticationTokenProvider {
    String getAccessTokenFromHeader(HttpServletRequest request);
    Authentication getAuthentication(String accessToken);
}
