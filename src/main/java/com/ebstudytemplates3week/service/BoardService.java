package com.ebstudytemplates3week.service;

import com.ebstudytemplates3week.domain.Board;
import com.ebstudytemplates3week.domain.Pagination;
import com.ebstudytemplates3week.domain.SearchFilter;
import com.ebstudytemplates3week.mapper.BoardMapper;
import com.ebstudytemplates3week.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    /**
     * 게시글의 총 갯수를 구한다
     *
     * @param searchFilter 카테고리, 키워드, 시작일, 종료일
     * @return 게시글의 총 갯수
     */
    public int getTotalCount(SearchFilter searchFilter) {
        return boardMapper.getTotalCount(searchFilter);
    }

    /**
     * 게시글 목록 출력에 필요한 파라미터만 추출해서 바인딩
     *
     * @param searchFilter 카테고리, 키워드, 시작일, 종료일
     * @param pagination 페이징 시작 번호, 한 페이지의 게시글 최대 갯수
     * @return 게시글 리스트
     */
    public List<Board> getBoardList(SearchFilter searchFilter, Pagination pagination) {

        Map<String, Object> map = new HashMap<>();

        map.put("category", searchFilter.getCategory());
        map.put("searchText", searchFilter.getSearchText());
        map.put("startDate", searchFilter.getStartDate());
        map.put("endDate", searchFilter.getEndDate());
        map.put("startNum", pagination.getStartNum());
        map.put("limit", pagination.getLimit());

        return boardMapper.getBoardList(map);
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

    /**
     * searchFilter 의 값을 지정한다
     *
     * @param searchFilter 검색 조건(시작일, 종료일, 카테고리, 키워드)
     * @return 검색 조건(시작일, 종료일, 카테고리, 키워드)
     */
    public SearchFilter getSearchFilter(SearchFilter searchFilter) {

        Utils utils = new Utils();

        int category = 0;
        String searchText = "";
        String startDate = utils.getStartDate() + " " + searchFilter.getSTART_TIME();
        String endDate = utils.getEndDate() + " " + searchFilter.getEND_TIME();

        if (searchFilter.getCategory() != 0) {
            category = searchFilter.getCategory();
        }

        if (searchFilter.getSearchText() != null) {
            searchText = searchFilter.getSearchText();
        }

        if (searchFilter.getStartDate() != null) {
            startDate = searchFilter.getStartDate() + " " + searchFilter.getSTART_TIME();
        }

        if (searchFilter.getEndDate() != null) {
            endDate = searchFilter.getEndDate() + " " + searchFilter.getEND_TIME();
        }

        return new SearchFilter(category, searchText, startDate, endDate);
    }
}