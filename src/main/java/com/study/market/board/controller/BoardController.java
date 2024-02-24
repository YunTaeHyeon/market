package com.study.market.board.controller;

import com.study.market.auth.JwtProvider;
import com.study.market.auth.SecurityUtil;
import com.study.market.board.domain.entity.Board;
import com.study.market.board.domain.RequestWriteBoardDto;
import com.study.market.board.domain.ResponseRetrieveBoardDto;
import com.study.market.board.service.BoardService;
import com.study.market.member.domain.entity.Member;
import com.study.market.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @GetMapping("/board")
    public Map<Long ,String> boardList() {
        return boardService.retrieveBoardList();
    }

    @PostMapping("/board/write")
    public void writeBoard(@RequestBody RequestWriteBoardDto dto) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberService.findMember(memberId);

        boardService.saveBoard(dto.getTitle(), dto.getContent(), member.getEmail());
    }

    @GetMapping("/board/retrieve/{id}")
    public ResponseRetrieveBoardDto retrieveBoard(@PathVariable("id") Long boardId) {
        return boardService.retrieveBoard(boardId);
    }

    @PatchMapping("/board/modify/{id}")
    public void modifyBoard(@PathVariable("id") Long boardId, @RequestBody RequestWriteBoardDto dto) {
        boardService.modifyBoard(boardId, dto.getTitle(), dto.getContent());
    }

    @DeleteMapping("/board/delete/{id}")
    public void deleteBoard(@PathVariable("id") Long boardId) {
        boardService.deleteBoard(boardId);
    }

}
