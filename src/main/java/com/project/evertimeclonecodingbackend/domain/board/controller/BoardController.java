package com.project.evertimeclonecodingbackend.domain.board.controller;

import com.project.evertimeclonecodingbackend.domain.board.dto.BoardSaveRequestDto;
import com.project.evertimeclonecodingbackend.domain.board.service.BoardService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping()
    public long save(@RequestBody BoardSaveRequestDto boardSaveRequestDto) {
        return boardService.save(boardSaveRequestDto.getTitle(), boardSaveRequestDto.getContent());
    }
}
