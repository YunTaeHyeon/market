package com.study.market.item.service;

import com.study.market.item.domain.ItemStatus;
import com.study.market.item.domain.entity.Cart;
import com.study.market.item.domain.entity.Item;
import com.study.market.item.domain.entity.ItemInCart;
import com.study.market.item.repository.ItemRepository;
import com.study.market.member.domain.entity.Member;
import com.study.market.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override //상품 등록
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

    @Override
    public void addCart(String email, String itemName) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("해당 이메일이 존재하지 않습니다."));

        Item item = itemRepository.findByItemName(itemName)
                .orElseThrow(()->new IllegalArgumentException("해당 아이템이 없습니다."));

        Cart cart = Cart.builder()
                .member(member)
                .build();

        ItemInCart itemInCart = ItemInCart.builder()
                .cart(cart)
                .item(item)
                .count(item.getStockNumber())
                .build();
    } //여기 서비스 로직까지 작성


}
