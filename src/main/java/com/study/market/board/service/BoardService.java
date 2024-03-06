package com.study.market.board.service;

import com.study.market.board.domain.PostsRequestDto;
import com.study.market.board.domain.ResponseRetrieveBoardDto;
import com.study.market.board.domain.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface BoardService {
    void saveBoard(String title, String content, String writer);
    void modifyBoard(Long boardId, String title, String content);
    void deleteBoard(Long boardId);
    Map<Long, String> retrieveBoardList();
    ResponseRetrieveBoardDto retrieveBoard(Long boardId);
    Page<PostsRequestDto> paging(Pageable pageable);
}
