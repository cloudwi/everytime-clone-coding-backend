package com.project.evertimeclonecodingbackend.domain.board.service;

import com.project.evertimeclonecodingbackend.domain.board.entity.Board;
import com.project.evertimeclonecodingbackend.domain.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public long save(String title, String content) {
        Board board = new Board(title, content);
        return boardRepository.save(board).getId();
    }


}
