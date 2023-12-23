package com.ebstudytemplates3week.mapper;

import com.ebstudytemplates3week.vo.Board;
import com.ebstudytemplates3week.vo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * id에 해당하는 댓글 조회
     * @param id
     * @return List<Comment>
     */
    List<Comment> getCommentByBoardId(String id);

    /**
     * 댓글 작성
     * @param comment 게시글번호, 댓글 내용
     */
    void insertComment(Comment comment);
}
