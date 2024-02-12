package com.study.market.manageMember;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.market.auth.JwtProvider;
import com.study.market.member.controller.MemberController;
import com.study.market.member.domain.RequestLoginDto;
import com.study.market.member.domain.RequestSignUpDto;
import com.study.market.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(MemberController.class)
public class ControllerUnitTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtProvider jwtProvider; //안하면 에러



    @Test
    @WithMockUser//(username = "최고관리자",roles = {"어쩌구저쩌구"}) //없으면 401
    //비로그인 상태에서 권한이 필요한 요청을 했을 때 발생하는 에러
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
                        .with(csrf()) //없으면 403
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
