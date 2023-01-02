package com.project.evertimeclonecodingbackend.domain.board.repository;

import com.project.evertimeclonecodingbackend.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
