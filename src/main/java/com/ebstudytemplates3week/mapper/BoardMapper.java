package com.ebstudytemplates3week.mapper;

import com.ebstudytemplates3week.domain.Board;
import com.ebstudytemplates3week.domain.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    // 총 게시글 수 출력
    int getTotalCount(Page page);

    // 게시글 리스트 출력
    List<Board> getBoardList(Page page);

    // 게시글 세부 조회
    List<Board> getBoardById(String id);

}
