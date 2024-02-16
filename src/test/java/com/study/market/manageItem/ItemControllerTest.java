package com.study.market.manageItem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.market.auth.JwtProvider;
import com.study.market.item.domain.ItemStatus;
import com.study.market.item.domain.RequestAddItemInCartDto;
import com.study.market.item.domain.RequestRegisterDto;
import com.study.market.item.domain.entity.*;
import com.study.market.item.repository.CartItemRepository;
import com.study.market.item.repository.CartRepository;
import com.study.market.item.repository.ItemRepository;
import com.study.market.item.repository.ReplyRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
    @Autowired
    private ReplyRepository replyRepository;

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

        //when
        for(int i=0; i<10; i++) {
            mockMvc.perform(MockMvcRequestBuilders.post("/item/cart")
                            .with(csrf())
                            .content(body)
                            .contentType(MediaType.APPLICATION_JSON));
        }

        ItemInCart itemInCart = cartItemRepository.findByCartIdAndItemId(
                cartRepository.findByMemberId(member.getId()).getId(), item.getId());

        //then
        assertThat(itemInCart.getCount()).isEqualTo(10);
    }

    @Test
    @DisplayName("멤버의 장바구니 페이지 조회")
    void 장바구니조회() throws Exception{
        //given
        itemService.saveItem("item",10000,1,"a",ItemStatus.SELL);
        itemService.saveItem("itemB",10002,1,"b",ItemStatus.SELL);

        memberService.join("yun","t@h","1234");

        Member member = memberRepository.findByEmail("t@h").orElseThrow();
        Item item = itemRepository.findByItemName("item").orElseThrow();
        Item itemB = itemRepository.findByItemName("itemB").orElseThrow();


        //when&then
        String body = objectMapper.writeValueAsString(
                RequestAddItemInCartDto.builder()
                        .itemId(item.getId())
                        .memberId(member.getId())
                        .build()
        );

        String bodyB = objectMapper.writeValueAsString(
                RequestAddItemInCartDto.builder()
                        .itemId(itemB.getId())
                        .memberId(member.getId())
                        .build()
        );


        mockMvc.perform(MockMvcRequestBuilders.post("/item/cart")
                    .with(csrf())
                    .content(body)
                    .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.post("/item/cart")
                .with(csrf())
                .content(bodyB)
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/item/cart/my-page")
                .with(csrf())
                .param("userId", String.valueOf(member.getId())))
                .andDo(print()); //id 1인것과 2인것 만들어진거 확인


        //then

        //assertThat(itemInCarts.get(0).getItem()).isSameAs(item); 서비스 로직에서 변수 받았을 때
        //assertThat(itemInCarts.get(1).getItem()).isSameAs(itemB);
    }

    @Test
    @DisplayName("주문 생성하기")
    void 주문생성() throws Exception{
        //given
        itemService.saveItem("item",10000,1,"a",ItemStatus.SELL);

        memberService.join("yun","t@h","1234");

        Member member = memberRepository.findByEmail("t@h").orElseThrow();
        Item item = itemRepository.findByItemName("item").orElseThrow();

        //when
        OrderItem orderItem = itemService.createOrder(item.getId(), member.getId(), 1);

        //then
        assertThat(orderItem.getItem()).isSameAs(item);
        assertThat(orderItem.getOrderPrice()).isEqualTo(10000);
    }

    @Test
    @DisplayName("댓글 생성하기")
    void 댓글달기() throws Exception{
        //given
        itemService.saveItem("item",10000,1,"a",ItemStatus.SELL);

        memberService.join("yun","t@h","1234");

        Member member = memberRepository.findByEmail("t@h").orElseThrow();
        Item item = itemRepository.findByItemName("item").orElseThrow();

        //when
        Reply reply = itemService.addReply(member.getId(), item.getId(), "최고에요");

        //then
        assertThat(reply.getContent()).isEqualTo("최고에요");
        assertThat(reply).isSameAs(item.getReplyList().get(0));
    }

    @Test
    @DisplayName("댓글 수정하기")
    void 댓글수정() throws Exception{
        //given
        itemService.saveItem("item",10000,1,"a",ItemStatus.SELL);

        memberService.join("yun","t@h","1234");

        Member member = memberRepository.findByEmail("t@h").orElseThrow();
        Item item = itemRepository.findByItemName("item").orElseThrow();

        //when
        Reply reply = itemService.addReply(member.getId(), item.getId(), "최고에요");

        reply.matchItem(item);

        itemService.modifyReply(item.getId(), reply.getId(), "최악이에요");

        //then
        assertThat(reply.getContent()).isEqualTo("최악이에요");
        assertThat(item.getReplyList().get(0)).isEqualTo(reply);
    }
}
