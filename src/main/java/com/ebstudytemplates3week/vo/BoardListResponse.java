package com.ebstudytemplates3week.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
