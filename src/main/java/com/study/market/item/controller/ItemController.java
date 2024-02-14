package com.study.market.item.controller;

import com.study.market.item.domain.RequestRegisterDto;
import com.study.market.item.domain.RequestAddItemInCartDto;
import com.study.market.item.domain.entity.Item;
import com.study.market.item.domain.entity.ItemInCart;
import com.study.market.item.domain.entity.OrderItem;
import com.study.market.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/cart/my-page")
    public List<ItemInCart> retrieveMyCart(@RequestParam("userId") Long id){ // localhost:8080/cart/mypage?userId=1
        return itemService.retrieveCart(id);
    }

    @PostMapping("/make-order")
    public OrderItem makeOrder(@RequestParam("userId") Long userId, @RequestBody Long itemId, int count){
        return itemService.createOrder(itemId,userId,count);
    }
}
