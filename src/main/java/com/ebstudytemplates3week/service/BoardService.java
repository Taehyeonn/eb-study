package com.ebstudytemplates3week.service;

import com.ebstudytemplates3week.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardMapper boardMapper;

    @Autowired
    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public String getWriter(int id) {
        return boardMapper.getWriter(id);
    }
}
