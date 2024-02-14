package com.study.market.item.controller;

import com.study.market.item.domain.RequestRegisterDto;
import com.study.market.item.domain.RequestAddItemInCartDto;
import com.study.market.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/register") //상품 등록
    public void registerItem(@RequestBody RequestRegisterDto dto){
        itemService.saveItem(dto.getItemName(),
                dto.getPrice(),
                dto.getStockNumber(),
                dto.getItemDetail(),
                dto.getItemStatus());
    }

    @PostMapping("/cart") //카트에 담기
    public void addItemInCart(@RequestBody RequestAddItemInCartDto dto){
        itemService.addCart(dto.getItemId(),dto.getMemberId());
    }

}
