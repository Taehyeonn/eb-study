package com.ebstudytemplates3week.service;

import com.ebstudytemplates3week.vo.Board;
import com.ebstudytemplates3week.vo.Comment;
import com.ebstudytemplates3week.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    /**
     * 댓글 목록 출력
     * @param id
     * @return List<Comment>
     */
    public List<Comment> getCommentByBoardId(String id) {
        return commentMapper.getCommentByBoardId(id);
    }

    /**
     * 댓글 작성
     * @param board board_id, content
     */
    public void insertComment(Board board) {
        commentMapper.insertComment(board);
    }
}
