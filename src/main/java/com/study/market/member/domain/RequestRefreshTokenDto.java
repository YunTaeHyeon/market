package com.study.market.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestRefreshTokenDto {

    private final String refreshToken;
}