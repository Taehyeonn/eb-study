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
public class ListService {
    private final BoardMapper boardMapper;
    private final CategoryMapper categoryMapper;

    public int getTotalCount(Page page) {
        return boardMapper.getTotalCount(page);
    }

    public List<Category> getCategoryList() {
        return categoryMapper.getCategoryList();
    }

    public List<Board> getBoardList(Page page) {
        return boardMapper.getBoardList(page);
    }
}
