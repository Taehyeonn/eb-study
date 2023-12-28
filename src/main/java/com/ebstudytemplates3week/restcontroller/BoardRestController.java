package com.ebstudytemplates3week.restcontroller;

import com.ebstudytemplates3week.Response.ResponseService;
import com.ebstudytemplates3week.Response.SingleResponse;
import com.ebstudytemplates3week.service.BoardService;
import com.ebstudytemplates3week.service.CategoryService;
import com.ebstudytemplates3week.service.CommentService;
import com.ebstudytemplates3week.service.FileService;
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
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final FileService fileService;

    @GetMapping("/list")
    public SingleResponse<BoardListResponse> getBoardList(
            @ModelAttribute("searchFilter") SearchFilter searchFilter,
            @ModelAttribute("pagination") Pagination pagination) {

        //페이지네이션
        pagination.setTotalCount(boardService.getTotalCount(searchFilter));

        //게시글 목록
        List<Board> boards = boardService.getBoardList(searchFilter, pagination);

        //카테고리 목록
        List<Category> categoryList = categoryService.getCategoryList();

        return responseService.getSingleResponse(new BoardListResponse(boards, pagination, categoryList, searchFilter));
    }

    @GetMapping("/view/{id}")
    public SingleResponse<ViewInfoResponse> getViewInfo(
            @PathVariable(name = "id") String id,
            @ModelAttribute("searchFilter") SearchFilter searchFilter,
            @ModelAttribute("pagination") Pagination pagination) {

        boardService.increaseViewCount(id);


        Board board = boardService.getBoardById(id);

        List<Comment> commentList = commentService.getCommentByBoardId(id);

        List<File> fileList = fileService.getFilesByBoardId(id);

        return responseService.getSingleResponse(new ViewInfoResponse(board, commentList, fileList));
    }
}
