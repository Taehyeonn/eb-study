package com.ebstudytemplates3week.service;

import com.ebstudytemplates3week.domain.Board;
import com.ebstudytemplates3week.domain.Comment;
import com.ebstudytemplates3week.mapper.BoardMapper;
import com.ebstudytemplates3week.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ViewService {
    private final BoardMapper boardMapper;
    private final CommentMapper commentMapper;

    //게시글 조회
    public List<Board> getBoardById(String id) {
        return boardMapper.getBoardById(id);
    }

    //조회수 증가
    public void increaseViewCount(String id) {
        boardMapper.increaseViewCount(id);
    }

    //댓글 리스트 조회
    public List<Comment> getCommentByBoardId(String id) {
        return commentMapper.getCommentByBoardId(id);
    }

    //댓글 작성
    public void insertComment(Comment comment) {
        commentMapper.insertComment(comment);
    }
}
