package com.study.market.member.controller;

import com.study.market.member.domain.RequestLoginDto;
import com.study.market.member.domain.ResponseLoginDto;
import com.study.market.member.domain.RequestSignUpDto;
import com.study.market.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody RequestSignUpDto dto){
        memberService.join(dto.getName(),dto.getEmail(),dto.getPassword());
    }

    @PostMapping("/login") //Post로 수정
    public ResponseLoginDto login(@RequestBody RequestLoginDto dto){
        return memberService.login(dto.getEmail(), dto.getPassword());
    }

}
