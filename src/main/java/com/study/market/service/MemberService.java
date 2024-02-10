package com.study.market.service;

import com.study.market.domain.Member;
import com.study.market.domain.ResponseLoginDto;

public interface MemberService {
    void join(String name, String email, String password);
    ResponseLoginDto login(String email, String password);

}
