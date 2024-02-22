package com.study.market.board.service;

import com.study.market.board.domain.Board;
import com.study.market.board.domain.ResponseRetrieveBoardDto;

import java.util.List;
import java.util.Map;

public interface BoardService {
    void saveBoard(String title, String content, String writer);
    void modifyBoard(Long boardId, String title, String content);
    void deleteBoard(Long boardId);
    Map<Long, String> retrieveBoardList();
    ResponseRetrieveBoardDto retrieveBoard(Long boardId);

}
