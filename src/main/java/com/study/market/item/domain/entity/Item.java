package com.study.market.item.domain.entity;

import com.study.market.item.domain.ItemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @OneToMany(mappedBy = "item")
    private List<Reply> replyList = new ArrayList<>();

    public void addReply(Reply reply){
        this.replyList.add(reply);
    }

    @Builder(toBuilder = true)
    public Item(long id, String itemName, int price, int stockNumber, String itemDetail, ItemStatus itemStatus){
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemStatus = itemStatus;
    }

    public void changeItemName(String itemName){
        this.itemName = itemName;
    }

    public void minusStockNumber(int count){
        this.stockNumber -= count;
    }
}
