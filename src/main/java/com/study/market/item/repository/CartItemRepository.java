package com.study.market.item.repository;

import com.study.market.item.domain.entity.ItemInCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<ItemInCart, Long> {
    ItemInCart findByCartIdAndItemId(Long cartId, Long itemId);
    List<ItemInCart> findAllByCartId(Long cartId);
}
