package com.study.market.auth;

import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.*;

/**
 * User 이상의 권한을 가진 사용자만 접근 가능한 API에 사용
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@SecurityReq(name = JwtProvider.AUTHORIZATION) 스웨거인듯
@Secured(UserAuthNames.ROLE_USER)
public @interface UserAuth {
}
