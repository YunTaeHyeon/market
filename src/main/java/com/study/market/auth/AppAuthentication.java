package com.study.market.auth;

import com.study.market.domain.UserRole;
import org.springframework.security.core.Authentication;

public interface AppAuthentication extends Authentication {
    Long getUserId();
    UserRole getUserRole();
    boolean isAdmin();
}
