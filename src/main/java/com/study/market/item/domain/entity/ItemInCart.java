package com.study.market.item.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="item_in_cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemInCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_in_cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    public void addCount(int count){
        this.count = count+1;
    }

    @Builder
    public ItemInCart(Cart cart, Item item, int count){
        this.cart = cart;
        this.item = item;
        this.count = count;
    }
}
//하나의 상품이 여러 장바구니에 들어갈 수 있음
//하나의 장바구니에 여러개의 상품이 들어갈 수 있음