package com.study.market.item.domain.entity;

import com.study.market.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order") //mappedBy가 있고 orderItem이 외래키가 있으니 orderItem이 주인
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime localDateTime;

    public void matchMember(Member member){
        this.member = member;
    }

}
