package com.study.market.item.repository;

import com.study.market.item.domain.entity.Item;
import com.study.market.item.domain.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemName(String itemName);
}
