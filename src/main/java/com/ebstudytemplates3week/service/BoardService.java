package com.ebstudytemplates3week.service;

import com.ebstudytemplates3week.domain.Board;
import com.ebstudytemplates3week.domain.Page;
import com.ebstudytemplates3week.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    // 게시글 총 갯수 출력
    public int getTotalCount(Page page) {
        return boardMapper.getTotalCount(page);
    }


    // 게시글 목록 출력
    public List<Board> getBoardList(Page page) {
        return boardMapper.getBoardList(page);
    }

    //게시글 조회
    public List<Board> getBoardById(String id) {
        return boardMapper.getBoardById(id);
    }

    //조회수 증가
    public void increaseViewCount(String id) {
        boardMapper.increaseViewCount(id);
    }

    //게시글 작성
    public void writeBoard(Board board) {
        boardMapper.writeBoard(board);
    }


}
