package com.ebstudytemplates3week.service;


import com.ebstudytemplates3week.domain.Board;
import com.ebstudytemplates3week.domain.Category;
import com.ebstudytemplates3week.domain.Comment;
import com.ebstudytemplates3week.mapper.BoardMapper;
import com.ebstudytemplates3week.mapper.CategoryMapper;
import com.ebstudytemplates3week.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WriteService {

    private final BoardMapper boardMapper;
    private final CategoryMapper categoryMapper;

    //게시글 작성
    public void writeBoard(Board board) {
        boardMapper.writeBoard(board);
    }

    //카테고리 선택
    public List<Category> getCategoryList() {
        return categoryMapper.getCategoryList();
    }


}
