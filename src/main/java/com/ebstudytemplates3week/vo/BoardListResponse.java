package com.ebstudytemplates3week.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardListResponse {

    private List<Board> boards;
    private Pagination pagination;
    private List<Category> categoryList;
    private SearchFilter searchFilter;

    public BoardListResponse(List<Board> boards, Pagination pagination, List<Category> categoryList, SearchFilter searchFilter) {
        this.boards = boards;
        this.pagination = pagination;
        this.categoryList = categoryList;
        this.searchFilter = searchFilter;
    }
}
