package com.study.market.auth;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    /**
     * SecurityUtil 클래스는 인증된 사용자의 정보를 제공하는 클래스입니다.
     */

    public static Long getCurrentMemberId() {
        final JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalArgumentException("Security Context에 인증 정보가 없습니다.");
        }
        return authentication.getUserId();
    }
}
