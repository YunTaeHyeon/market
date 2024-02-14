package com.study.market.manageMember;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.market.member.domain.*;
import com.study.market.member.domain.RequestLoginDto;
import com.study.market.member.domain.RequestSignUpDto;
import com.study.market.member.service.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    public void saveTest() throws Exception{
        //given
        String body = objectMapper.writeValueAsString(
                RequestSignUpDto.builder()
                        .email("t@h")
                        .name("yun")
                        .password("1234")
                        .build()
        );

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/sign-up")
                .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("로그인 테스트")
    public void login() throws Exception{
        //given
        memberService.join("yun", "t@h", "1234");

        String body = objectMapper.writeValueAsString(
                RequestLoginDto.builder()
                        .email("t@h")
                        .password("1234")
                        .build()
        );

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
