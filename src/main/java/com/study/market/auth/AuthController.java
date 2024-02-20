package com.study.market.auth;

import com.study.market.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    //@GetMapping("/reissue")
    //public ResponseEntity<Result> refreshToken(@RequestHeader("X-AUTH-TOKEN"))
    //ToDo: 쿠키 공부 후 JWT를 쿠키로 만들어서 reissue 할 것.
}
