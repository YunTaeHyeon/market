package com.study.market.item.domain.entity;

import com.study.market.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reply {
    @Id
    @Column(name = "reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //ToDo: 리뷰 사진도 넣을 수 있게 구현

    @Lob
    private String content;

    public void changeContent(String content){
        this.content = content;
    }

    public void matchItem(Item item){
        this.item=item;
        item.addReply(this);
    }

    @Builder
    public Reply(Member member, Item item, String content){
        this.member=member;
        this.item=item;
        this.content=content;
    }
}
