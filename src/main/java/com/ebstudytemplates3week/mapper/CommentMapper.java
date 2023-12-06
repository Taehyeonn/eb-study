package com.ebstudytemplates3week.mapper;

import com.ebstudytemplates3week.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    // 댓글 조회
    List<Comment> getCommentByBoardId(String id);

    // 댓글 작성
    void insertComment(Comment comment);
}
