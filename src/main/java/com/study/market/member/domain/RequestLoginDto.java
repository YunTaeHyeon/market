package com.study.market.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestLoginDto {
    private String email;
    private String password;
}
//이렇게 하면 안되지만 RequestSignUpDto와 대조를 위해
