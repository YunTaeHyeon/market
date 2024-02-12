package com.study.market.member.domain;

import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestSignUpDto {
    private String name;
    private String email;
    private String password;

    @Builder
    public RequestSignUpDto(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

/*
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestSignUpDto {
    private String name;
    private String email;
    private String password;

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