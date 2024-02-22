package com.study.market.board.service;

import com.study.market.board.domain.Board;
import com.study.market.board.domain.ResponseRetrieveBoardDto;
import com.study.market.board.repository.BoardRepository;
import com.study.market.item.repository.ItemRepository;
import com.study.market.member.domain.entity.Member;
import com.study.market.member.repository.MemberRepository;
import com.study.market.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public void saveBoard(String title, String content, String writer) {
        Member member = memberRepository.findByEmail(writer)
                .orElseThrow(()->new IllegalArgumentException("해당 이메일이 존재하지 않습니다."));

        Board board = Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();

        log.info("board : {}", board);

        boardRepository.save(board);
    }

    @Override
    @Transactional
    public void modifyBoard(Long boardId, String title, String content) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        board.changeTitle(title);
        board.changeContent(content);

        boardRepository.save(board);
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        boardRepository.delete(board);
    }

    @Override
    public Map<Long, String> retrieveBoardList() {
        List<Board> boardList = boardRepository.findAll();

        //boardList에서 title만 추출한 리스트인 boardName 반환
        Map<Long, String> boardNameMap = new HashMap();

        for(Board board : boardList){
            boardNameMap.put(board.getId(), board.getTitle());
        }

        return boardNameMap;
    }

    @Override
    @Transactional
    public ResponseRetrieveBoardDto retrieveBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        board.addViewCount();

        return ResponseRetrieveBoardDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getMember().getEmail())
                .viewCount(board.getViewCount())
                .build();
    }
}
