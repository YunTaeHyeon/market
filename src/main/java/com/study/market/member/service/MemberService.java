package com.study.market.member.service;

import com.study.market.member.domain.ResponseLoginDto;
import com.study.market.member.domain.ResponseRefreshTokenDto;
import com.study.market.member.domain.entity.Member;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface MemberService {
    void join(String name, String email, String password);
    ResponseLoginDto login(String email, String password);
    ResponseRefreshTokenDto refreshToken(HttpServletRequest request, String refreshToken);
    Member findMember(Long memberId);

}
