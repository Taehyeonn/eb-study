package com.ebstudytemplates3week.controller;


import com.ebstudytemplates3week.domain.Board;
import com.ebstudytemplates3week.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/free")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
//    public String getWriter(@PathVariable String id) {
    public String getWriter() {

        return boardService.getWriter(1);
    }
}
