package com.study.market.item.service;

import com.study.market.item.domain.ItemStatus;
import com.study.market.item.domain.entity.*;
import com.study.market.item.repository.CartItemRepository;
import com.study.market.item.repository.CartRepository;
import com.study.market.item.repository.ItemRepository;
import com.study.market.member.domain.entity.Member;
import com.study.market.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

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
    @Transactional
    @Override
    public void addCart(Long itemId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일이 존재하지 않습니다."));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다."));

        Cart cart = cartRepository.findByMemberId(member.getId());

        if (cart == null) {
            cart = Cart.builder()
                    .member(member)
                    .build();

            log.info("cart: {}", cart);

            cartRepository.save(cart);
        }

        ItemInCart saveItemInCart = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if (saveItemInCart != null) {
            saveItemInCart.addCount(saveItemInCart.getCount());
        } else {
            ItemInCart itemInCart = ItemInCart.builder()
                    .cart(cart)
                    .item(item)
                    .count(1)
                    .build();

            log.info("itemInCart: {}", itemInCart);

            cartItemRepository.save(itemInCart);
        }

    }

    @Override
    public List<ItemInCart> retrieveCart(Long memberId) {
        Cart cart = cartRepository.findByMemberId(memberId);

        log.info("retrieve "+cart.getMember().getEmail());

        return cartItemRepository.findAllByCartId(cart.getMember().getId());
    }

    @Override
    public OrderItem createOrder(Long itemId, Long memberId, int count) { //몇개 주문할건지
        Item item = itemRepository.findById(itemId).orElseThrow(()-> new IllegalArgumentException("아이템이 존재하지 않습니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new IllegalArgumentException("멤버가 존재하지 않습니다."));

        Order order = new Order();
        order.matchMember(member);

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .orderPrice(item.getPrice()*count)
                .item(item)
                .count(count)
                .build();

        if(item.getStockNumber() < count){
            throw new RuntimeException("재고가 부족합니다.");
        }else {
            item.minusStockNumber(count);
        }
        log.info("orderItem: {}", orderItem);

        return orderItem;
    }
}
