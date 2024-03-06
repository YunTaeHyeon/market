package com.study.market.board.domain;

import com.study.market.board.domain.entity.Board;
import lombok.Getter;

@Getter
public class PostsRequestDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsRequestDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getMember().getEmail();
    }
}
