package com.study.market.item.domain;

import com.study.market.item.service.ItemService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestRegisterDto {
    String itemName;
    int price;
    int stockNumber;
    String itemDetail;
    ItemStatus itemStatus;

    @Builder
    public RequestRegisterDto(String itemName, int price,
                       int stockNumber, String itemDetail
                        ,ItemStatus itemStatus){

        this.itemName = itemName;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemStatus = itemStatus;
    }
}
