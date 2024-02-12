package com.study.market.manageMember;

import com.study.market.member.domain.entity.Member;
import com.study.market.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ManageMemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveMember(){

        //given
        memberRepository.save(Member.builder()
                .id(1L)
                .name("yun")
                .email("t@h")
                .password("1234")
                .build());

        //when
        List<Member> memberList = memberRepository.findAll();

        //then
        Member member = memberList.get(0);
        Assertions.assertEquals(member.getId(),1L);
    }
}
