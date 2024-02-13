package com.study.market.item.service;

import com.study.market.item.domain.ItemStatus;

public interface ItemService {
    void saveItem(String itemName, int price, int stockNumber, String itemDetail, ItemStatus itemStatus);
    void addCart(String email, String itemName);
}
