package com.study.market.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestSignUpDto {
    private String name;
    private String email;
    private String password;
}

/*
@Getter
@NoArgsConstructor
public class RequestSignUpDto {
    private String name;
    private String email;
    private String password;

    @Builder
    public RequestSignUpDto(String name, String email, String password){
        this.name=name;
        this.email=email;
        this.password=password;
    }
}
*/

/*
@Getter
public class RequestSignUpDto {
    private String name;
    private String email;
    private String password;

    } -> 였을때 에러가 발생했었음

 */