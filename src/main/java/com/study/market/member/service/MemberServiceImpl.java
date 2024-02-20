package com.study.market.member.service;

import com.study.market.auth.AuthenticationToken;
import com.study.market.auth.JwtProvider;
import com.study.market.member.domain.entity.Member;
import com.study.market.member.domain.ResponseLoginDto;
import com.study.market.member.domain.UserRole;
import com.study.market.member.repository.MemberRepository;
import com.study.market.redis.RedisService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

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

        Long durationToMilli = jwtProvider.getRefreshExpiration().toMillis();
        //duration으로 되어있는걸 Milli로 포멧팅
        redisService.setStringValue(token.getRefreshToken(), member.getEmail(), durationToMilli);
        //redis에 저장

        return new ResponseLoginDto(token);
    }

}
