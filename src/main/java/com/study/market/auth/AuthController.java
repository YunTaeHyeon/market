package com.study.market.auth;

import com.study.market.member.domain.RequestRefreshTokenDto;
import com.study.market.member.domain.ResponseRefreshTokenDto;
import com.study.market.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @PostMapping("/reissue")
    @UserAuth
    public ResponseRefreshTokenDto refreshToken(HttpServletRequest request,
                                                @RequestBody RequestRefreshTokenDto dto) {
        return memberService.refreshToken(request, dto.getRefreshToken());
    }
}
