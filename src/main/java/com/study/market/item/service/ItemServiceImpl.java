package com.study.market.item.service;

import com.study.market.item.domain.ItemStatus;
import com.study.market.item.domain.entity.Item;
import com.study.market.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public void saveItem(String itemName, int price, int stockNumber, String itemDetail, ItemStatus itemStatus) {

        Item item = Item.builder()
                .itemName(itemName)
                .price(price)
                .stockNumber(stockNumber)
                .itemDetail(itemDetail)
                .itemStatus(itemStatus)
                .build();

        log.info("Item: {}", item);

        itemRepository.save(item);
    }
}
