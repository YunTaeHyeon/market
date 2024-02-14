package com.study.market.manageItem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.market.auth.JwtProvider;
import com.study.market.item.domain.ItemStatus;
import com.study.market.item.domain.RequestAddItemInCartDto;
import com.study.market.item.domain.entity.Item;
import com.study.market.item.domain.entity.ItemInCart;
import com.study.market.item.repository.CartItemRepository;
import com.study.market.item.repository.CartRepository;
import com.study.market.item.repository.ItemRepository;
import com.study.market.item.service.ItemService;
import com.study.market.member.domain.entity.Member;
import com.study.market.member.repository.MemberRepository;
import com.study.market.member.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ItemControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private JwtProvider jwtProvider;


    @Test
    @DisplayName("장바구니에 담기 테스트")
    public void 장바구니에담기테스트() throws Exception{
        //given
        itemService.saveItem("item",10000,1,"a",ItemStatus.SELL);

        memberService.join("yun","t@h","1234");

        Member member = memberRepository.findByEmail("t@h").orElseThrow();
        Item item = itemRepository.findByItemName("item").orElseThrow();


        String body = objectMapper.writeValueAsString(
                RequestAddItemInCartDto.builder()
                        .itemId(item.getId())
                        .memberId(member.getId())
                        .build()
        );

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/item/cart")
                .with(csrf())
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        //장바구니에 아이템 담는 테스트


        ItemInCart itemInCart = cartItemRepository.findByCartIdAndItemId(
                cartRepository.findByMemberId(member.getId()).getId(), item.getId());

        Assertions.assertEquals(itemInCart.getItem(), item);
        Assertions.assertEquals(itemInCart.getCart(), cartRepository.findByMemberId(member.getId()));
        //장바구니에 넣은 아이템이 해당 멤버의 카트로 들어간게 맞는지, 그 아이템이 맞는지 테스트

        mockMvc.perform(MockMvcRequestBuilders.post("/item/cart")
                        .with(csrf())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(itemInCart.getCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 아이템 개수 세기")
    public void 장바구니아이템개수세기() throws Exception{
        //given
        itemService.saveItem("item",10000,1,"a",ItemStatus.SELL);

        memberService.join("yun","t@h","1234");

        Member member = memberRepository.findByEmail("t@h").orElseThrow();
        Item item = itemRepository.findByItemName("item").orElseThrow();


        String body = objectMapper.writeValueAsString(
                RequestAddItemInCartDto.builder()
                        .itemId(item.getId())
                        .memberId(member.getId())
                        .build()
        );

        //when & then

        for(int i=0; i<10; i++) {
            mockMvc.perform(MockMvcRequestBuilders.post("/item/cart")
                            .with(csrf())
                            .content(body)
                            .contentType(MediaType.APPLICATION_JSON));
        }

        ItemInCart itemInCart = cartItemRepository.findByCartIdAndItemId(
                cartRepository.findByMemberId(member.getId()).getId(), item.getId());

        assertThat(itemInCart.getCount()).isEqualTo(10);
    }
}
