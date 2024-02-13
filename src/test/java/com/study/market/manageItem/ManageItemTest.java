package com.study.market.manageItem;

import com.study.market.item.domain.ItemStatus;
import com.study.market.item.domain.entity.Cart;
import com.study.market.item.domain.entity.Item;
import com.study.market.item.domain.entity.ItemInCart;
import com.study.market.item.repository.ItemRepository;
import com.study.market.member.domain.entity.Member;
import com.study.market.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest //@Transactional이 포함되어 있음
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ManageItemTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("상품 레포에 등록")
    void saveItem(){

        //given
        itemRepository.save(Item.builder()
                .id(1L)
                .itemName("item")
                .price(10000)
                .stockNumber(1)
                .itemDetail(".")
                .itemStatus(ItemStatus.SELL)
                .build());

        //when
        List<Item> itemList = itemRepository.findAll();

        //then
        Item item = itemList.get(0);
        Assertions.assertEquals(item.getId(), 1L);
    }

    @Test
    @DisplayName("상품 이름 변경")
    void changeItemName(){
        //given
        Item item = itemRepository.save(Item.builder()
                .id(1L)
                .itemName("itemA")
                .price(10000)
                .stockNumber(1)
                .itemDetail(".")
                .itemStatus(ItemStatus.SELL)
                .build());

        //when
        Item updateItem = item.toBuilder().itemName("itemB")
                .build();

        itemRepository.save(updateItem);

        Item itemB=itemRepository.findByItemName("itemB")
                .orElseThrow(()-> new RuntimeException("no Item"));

        //then
        assertThat(item).isEqualTo(itemB); //내용을 비교
        assertThat(item).isSameAs(itemB); //주소값을 비교
    }
    @Test
    @DisplayName("상품 이름 변경2")
    void changeItemName2(){
        //given
        Item item = itemRepository.save(Item.builder()
                .id(1L)
                .itemName("itemA")
                .price(10000)
                .stockNumber(1)
                .itemDetail(".")
                .itemStatus(ItemStatus.SELL)
                .build());

        //when
        item.changeItemName("itemB");

        Item itemB=itemRepository.findByItemName("itemB")
                .orElseThrow(()-> new RuntimeException("no Item"));

        //then
        assertThat(item).isEqualTo(itemB); //내용을 비교
        assertThat(item).isSameAs(itemB); //주소값을 비교
    }

    @Test
    @DisplayName("장바구니 테스트")
    void AddCart(){
        //given
        Item item = itemRepository.save(Item.builder()
                .id(1L)
                .itemName("itemA")
                .price(10000)
                .stockNumber(1)
                .itemDetail(".")
                .itemStatus(ItemStatus.SELL)
                .build());

        Member member = memberRepository.save(Member.builder()
                .id(1L)
                .name("yun")
                .email("t@h")
                .password("1234")
                .build());

        Cart cart = Cart.builder()
                .member(member)
                .build();

        ItemInCart itemInCart = ItemInCart.builder()
                .item(item)
                .cart(cart)
                .count(item.getStockNumber())
                .build();

        Assertions.assertEquals(item.getId(),itemInCart.getItem().getId());
        Assertions.assertEquals(member.getId(),itemInCart.getCart().getMember().getId());
    }
}
