package com.ebstudytemplates3week.service;

import com.ebstudytemplates3week.vo.Board;
import com.ebstudytemplates3week.vo.Pagination;
import com.ebstudytemplates3week.vo.PasswordVerification;
import com.ebstudytemplates3week.vo.SearchFilter;
import com.ebstudytemplates3week.exception.PasswordMismatchException;
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

        Map<String, Object> map = new HashMap<>(); //todo 파라미터 그대로 보내기. map은 너무 자유로워서. 실수방지

        map.put("category", searchFilter.getCategory());
        map.put("searchText", searchFilter.getSearchText());
        map.put("startDate", searchFilter.getStartDate());
        map.put("endDate", searchFilter.getEndDate());
        map.put("END_TIME", searchFilter.getEND_TIME());
        map.put("START_TIME", searchFilter.getSTART_TIME());
        map.put("startNum", pagination.getStartNum());
        map.put("limit", pagination.getLimit());

        return boardMapper.getBoardList(map);
    }

    /**
     * id에 해당하는 게시글 상세 조회
     * @param id board_id
     * @return List<Board>
     */
    public List<Board> getBoardById(String id) {
        return boardMapper.getBoardById(id);
    }

    /**
     * id에 해당하는 조회수 증가
     * @param id board_id
     */
    public void increaseViewCount(String id) {
        boardMapper.increaseViewCount(id);
    }

    /**
     * 입력받은 board 파라미터를 이용해 게시글 작성
     * @param board category_id, writer, password, title, content
     */
    public void writeBoard(Board board) {
        boardMapper.writeBoard(board);
    }

    /**
     * 게시글 수정
     * @param board writer, title, content
     */
    public void modifyBoard(Board board) {
        boardMapper.modifyBoard(board);
    }

    /**
     * 게시글 삭제
     * @param id 삭제할 게시글 번호
     */
    public void deleteBoard(String id) {
        boardMapper.deleteBoard(id);
    }

    /**
     * 게시글과 비밀번호 일치 여부 확인
     * @param passwordVerification 게시글 번호, 비밀번호
     * @return 불일치시 0
     */
    public int passwordCheck(PasswordVerification passwordVerification) {
        return boardMapper.passwordCheck(passwordVerification);
    }
}
