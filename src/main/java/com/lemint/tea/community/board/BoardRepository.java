package com.lemint.tea.community.board;

import com.lemint.tea.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {
}
