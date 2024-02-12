package com.study.market.item.domain.entity;

import com.study.market.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.*;

//장바구니 하나는 유저 하나와 매핑됨.
@Entity
@Table(name="cart")
@Getter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void matchMember(Member member) {
        this.member = member;
    }
}
