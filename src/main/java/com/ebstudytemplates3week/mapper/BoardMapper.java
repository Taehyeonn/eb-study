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

    /**
     * 총 게시글 수 조회
     * @param searchFilter startDate, endDate, category, searchText
     * @return 총 게시글 수
     */
    int getTotalCount(SearchFilter searchFilter);

    /**
     * 게시글 리스트 조회
     * @param map startDate, endDate, category, searchText, startNum, limit
     * @return List<Board>
     */
    List<Board> getBoardList(Map<String, Object> map);

    /**
     * id와 매칭되는 게시글 세부 조회
     * @param id
     * @return List<Board>
     */
    List<Board> getBoardById(String id);

    /**
     * id의 조회수 증가
     * @param id
     */
    void increaseViewCount(String id);

    /**
     * 게시글 작성
     * @param board category_id, writer, password, title, content
     */
    void writeBoard(Board board);

    /**
     * 게시글 수정
     * @param board id, writer, title, content
     */
    void modifyBoard(Board board);

    /**
     * 비밀번호 일치 여부 확인
     * @param board id, password
     * @return 불일치시 0
     */
    int passwordCheck(Board board);
}
