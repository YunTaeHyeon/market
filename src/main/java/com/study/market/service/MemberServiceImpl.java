package com.study.market.service;

import com.study.market.auth.AuthenticationToken;
import com.study.market.auth.JwtAuthentication;
import com.study.market.auth.JwtProvider;
import com.study.market.domain.Member;
import com.study.market.domain.ResponseLoginDto;
import com.study.market.domain.UserRole;
import com.study.market.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void join(String name, String email, String password) {

        if(memberRepository.existsByEmail(email)){
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .userRole(UserRole.USER)
                .build();

        log.info("member: {}" , member);

        memberRepository.save(member);
    }

    @Override
    public ResponseLoginDto login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 이메일입니다"));

        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        AuthenticationToken token = jwtProvider.issue(member);
        return new ResponseLoginDto(token);
    }

}
