package com.study.market.item.domain.entity;

import com.study.market.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.*;

//장바구니 하나는 유저 하나와 매핑됨.
@Entity
@Table(name="cart")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder //되는지 안되는지 모름
    public Cart(Member member){
        this.member = member;
    }
}
