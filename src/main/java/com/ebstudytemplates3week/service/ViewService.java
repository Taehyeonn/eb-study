package com.ebstudytemplates3week.service;

import com.ebstudytemplates3week.domain.Board;
import com.ebstudytemplates3week.domain.Category;
import com.ebstudytemplates3week.domain.Page;
import com.ebstudytemplates3week.mapper.BoardMapper;
import com.ebstudytemplates3week.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ViewService {
    private final BoardMapper boardMapper;

    public List<Board> getBoardById(String id) {
        return boardMapper.getBoardById(id);
    }
}
