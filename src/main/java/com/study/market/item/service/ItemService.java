package com.study.market.item.service;

import com.study.market.item.domain.ItemStatus;
import com.study.market.item.domain.entity.ItemInCart;
import com.study.market.item.domain.entity.OrderItem;

import java.util.List;

public interface ItemService {
    void saveItem(String itemName, int price, int stockNumber, String itemDetail, ItemStatus itemStatus);
    void addCart(Long itemId, Long memberId);
    List<ItemInCart> retrieveCart(Long memberId);
    OrderItem createOrder(Long itemId, Long memberId, int count);

}
