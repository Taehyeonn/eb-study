package com.ebstudytemplates3week.restcontroller;

import com.ebstudytemplates3week.Response.ResponseService;
import com.ebstudytemplates3week.Response.SingleResponse;
import com.ebstudytemplates3week.service.BoardService;
import com.ebstudytemplates3week.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @GetMapping("/list")
    public SingleResponse<BoardListResponse> getBoardList(
            @ModelAttribute("searchFilter") SearchFilter searchFilter,
            @ModelAttribute("pagination") Pagination pagination) {

        //페이지네이션
        pagination.setTotalCount(boardService.getTotalCount(searchFilter));

        //Search에서
//        //카테고리 목록
//        List<Category> categoryList = categoryService.getCategoryList();

        List<Board> boards = boardService.getBoardList(searchFilter, pagination);

        BoardListResponse boardListResponse = new BoardListResponse(boards, pagination);

        return responseService.getSingleResponse(boardListResponse);
    }
}
