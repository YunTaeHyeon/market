package com.study.market.board.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseRetrieveBoardDto {
    private String title;
    private String content;
    private String writer;
    private int viewCount;
    private String isWriter;

    @Builder
    public ResponseRetrieveBoardDto(String title, String content, String writer, int viewCount, String isWriter){
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.viewCount = viewCount;
        this.isWriter = isWriter;
    }
}
