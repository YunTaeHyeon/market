package com.study.market.board.repository;

import com.study.market.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Board, Long> {
}
