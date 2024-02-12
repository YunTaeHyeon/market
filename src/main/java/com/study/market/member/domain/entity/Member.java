package com.study.market.member.domain.entity;

import com.study.market.member.domain.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED) //없으면 레포지토리 테스트 실패
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;

    @Builder
    public Member(long id, String name, String email, String password, UserRole userRole){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }
}
