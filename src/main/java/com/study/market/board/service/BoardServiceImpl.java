package com.study.market.board.service;

import com.study.market.auth.SecurityUtil;
import com.study.market.board.domain.PostsRequestDto;
import com.study.market.board.domain.entity.Board;
import com.study.market.board.domain.ResponseRetrieveBoardDto;
import com.study.market.board.repository.BoardRepository;
import com.study.market.board.repository.PostsRepository;
import com.study.market.item.repository.ItemRepository;
import com.study.market.member.domain.entity.Member;
import com.study.market.member.repository.MemberRepository;
import com.study.market.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final PostsRepository postsRepository;

    @Override
    @Transactional
    public void saveBoard(String title, String content, String writer) {
        Member member = memberRepository.findByEmail(writer)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일이 존재하지 않습니다."));

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
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        board.changeTitle(title);
        board.changeContent(content);

        boardRepository.save(board);
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        /*
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        if (board.getMember().getId() != member.getId()) {
            throw new IllegalArgumentException("해당 게시글을 삭제할 권한이 없습니다.");
        }
        //게시글 작성자만 삭제 가능 -> 작성자에게만 삭제 버튼이 보여서 이 부분은 필요 없음
         */

        boardRepository.delete(board);
    }

    @Override
    public Map<Long, String> retrieveBoardList() {
        List<Board> boardList = boardRepository.findAll();

        //boardList에서 title만 추출한 리스트인 boardName 반환
        Map<Long, String> boardNameMap = new HashMap();

        for (Board board : boardList) {
            boardNameMap.put(board.getId(), board.getTitle());
        }

        return boardNameMap;
    }

    @Override
    @Transactional
    public ResponseRetrieveBoardDto retrieveBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        if (board.getMember().getId() != member.getId()) {
            board.addViewCount(); //본인이 아닌 사람의 게시물 볼 때만 조회수 증가

            return ResponseRetrieveBoardDto.builder()
                    .title(board.getTitle())
                    .content(board.getContent())
                    .writer(board.getMember().getEmail())
                    .viewCount(board.getViewCount())
                    .isWriter("false")
                    .build();
        }

        return ResponseRetrieveBoardDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getMember().getEmail())
                .viewCount(board.getViewCount())
                .isWriter("true")
                .build();
    }

    @Override
    public Page<PostsRequestDto> paging(Pageable pageable){
        int page = pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1;
        int pageLimit = 10; //한 페이지에 보여줄 게시글 수

        Page<Board> boardPage = postsRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        //board_id를 기준으로 내림차순 정렬

        Page<PostsRequestDto> postsRequestDtoPage = boardPage.map(PostsRequestDto::new);

        //Page<PostsRequestDto> postsRequestDtoPage = boardPage.map(board -> new PostsRequestDto(board));
        //위와 같은 코드

        return postsRequestDtoPage;
    }

}
