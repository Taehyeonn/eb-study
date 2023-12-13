package com.ebstudytemplates3week.mapper;

import com.ebstudytemplates3week.domain.Board;
import com.ebstudytemplates3week.domain.SearchFilter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    // 총 게시글 수 출력
    int getTotalCount(SearchFilter searchFilter);

    // 게시글 리스트 출력
    List<Board> getBoardList(Map<String, Object> map);

    // 게시글 세부 조회
    List<Board> getBoardById(String id);

    //조회수 증가
    void increaseViewCount(String id);

    //게시글 작성
    void writeBoard(Board board);

    // 글수정
    void modifyBoard(Board board);

    // 비밀번호 확인
    int passwordCheck(Board board);

}
