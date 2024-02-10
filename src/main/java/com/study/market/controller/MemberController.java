package com.study.market.controller;

import com.study.market.domain.RequestLoginDto;
import com.study.market.domain.RequestSignUpDto;
import com.study.market.domain.ResponseLoginDto;
import com.study.market.repository.MemberRepository;
import com.study.market.service.MemberService;
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

    @GetMapping("/login")
    public ResponseLoginDto login(@RequestBody RequestLoginDto dto){
        return memberService.login(dto.getEmail(), dto.getPassword());
    }

}
