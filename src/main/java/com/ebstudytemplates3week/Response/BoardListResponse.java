package com.ebstudytemplates3week.Response;

import com.ebstudytemplates3week.vo.Board;
import com.ebstudytemplates3week.vo.Pagination;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ToString
public class BoardListResponse {

    private List<Board> boards;
    private Pagination pagination;

    public BoardListResponse(List<Board> boards, Pagination pagination) {
        this.boards = boards;
        this.pagination = pagination;
    }
}
