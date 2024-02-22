package com.study.market.board.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseRetrieveBoardDto {
    private String title;
    private String content;
    private String writer;
    private int viewCount;

    @Builder
    public ResponseRetrieveBoardDto(String title, String content, String writer, int viewCount){
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.viewCount = viewCount;
    }
}
