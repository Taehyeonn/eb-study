package com.ebstudytemplates3week.service;

import com.ebstudytemplates3week.domain.Comment;
import com.ebstudytemplates3week.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    //댓글 리스트 조회
    public List<Comment> getCommentByBoardId(String id) {
        return commentMapper.getCommentByBoardId(id);
    }

    //댓글 작성
    public void insertComment(Comment comment) {
        commentMapper.insertComment(comment);
    }
}
