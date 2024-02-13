package com.study.market.item.controller;

import com.study.market.item.domain.RequestRegisterDto;
import com.study.market.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/item/register") //상품 등록
    public void registerItem(@RequestBody RequestRegisterDto requestRegisterDto){
        itemService.saveItem(requestRegisterDto.getItemName(),
                requestRegisterDto.getPrice(),
                requestRegisterDto.getStockNumber(),
                requestRegisterDto.getItemDetail(),
                requestRegisterDto.getItemStatus());
    }

}
