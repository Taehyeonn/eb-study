package com.ebstudytemplates3week.mapper;

import com.ebstudytemplates3week.domain.Board;
import com.ebstudytemplates3week.domain.Comment;
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
     * id에 해당하는 댓글 조회
     * @param board id, content
     */
    void insertComment(Board board);
}
