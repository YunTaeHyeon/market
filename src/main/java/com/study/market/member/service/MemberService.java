package com.study.market.member.service;

import com.study.market.member.domain.ResponseLoginDto;

public interface MemberService {
    void join(String name, String email, String password);
    ResponseLoginDto login(String email, String password);

}
