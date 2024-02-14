package com.study.market.item.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestAddItemInCartDto {
    private Long itemId;
    private Long memberId;

    @Builder
    public RequestAddItemInCartDto(Long itemId, Long memberId){
        this.itemId = itemId;
        this.memberId = memberId;
    }
}
