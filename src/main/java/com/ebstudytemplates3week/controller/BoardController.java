package com.ebstudytemplates3week.controller;

import com.ebstudytemplates3week.domain.*;
import com.ebstudytemplates3week.service.BoardService;
import com.ebstudytemplates3week.service.CategoryService;
import com.ebstudytemplates3week.service.CommentService;
import com.ebstudytemplates3week.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board/free")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    /**
     * 검색 조건을 이용해 게시글 목록과 페이지 네비게이션 출력
     *
     * @param sf 검색 조건(시작일, 종료일, 카테고리, 키워드)
     * @param pg 페이지네이션(pageNum)
     * @param model searchFilter, pagination, boardList, categoryList
     * @return list
     */
    @GetMapping("/list")
    public String getList(@ModelAttribute("searchFilter") SearchFilter sf,
                          @ModelAttribute("pagination") Pagination pg,
                          Model model) {

        //검색 필터 셋팅
        SearchFilter searchFilter = boardService.getSearchFilter(sf);
        model.addAttribute("searchFilter", searchFilter);

        //페이지네이션
        Pagination pagination = new Pagination();
        pagination.setPageNum(pg.getPageNum());
        pagination.setTotalCount(boardService.getTotalCount(searchFilter));

        model.addAttribute("pagination", pagination);

        //게시글 목록
        model.addAttribute("boardList", boardService.getBoardList(searchFilter, pagination));

        //카테고리 목록
        List<Category> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        return "list";
    }
    //todo: 쿼리랑 파라미터를 나눠서 전달,

    //상세조회
    @GetMapping("/view")
    public String getViewInfo(
            Model model,
            @RequestParam("id") String id,
            @RequestParam("pageNum") String pageNum) {

        boardService.increaseViewCount(id);

        model.addAttribute("board", boardService.getBoardById(id));
        model.addAttribute("commentList", commentService.getCommentByBoardId(id));
        model.addAttribute("id", id); //todo 굳이 필요한지
        model.addAttribute("pageNum", pageNum);

        return "view";
    }
    //todo: 잘못된 id,pageNum 예외처리



    // write 페이지에 필요한 카테고리, pageNum
    @GetMapping("/write")
    public String getWriteInfo(
            @RequestParam("pageNum") String pageNum,
            Model model) {

        List<Category> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("pageNum", pageNum);

        return "write";
    }

    // 유효성 검사 로직 성공시 글 등록
    @PostMapping("/write")
    public String writeBoard(
            @ModelAttribute("board") Board board,
            @RequestParam("confirmPassword") String confirmPassword){

        Validation validation = new Validation();

        log.info("board ={}", board);

        /**
         * 서버 유효성 검사 수행후 실패시 에러 페이지 발생
         */
        if (validation.isNotValidCategory(String.valueOf(board.getCategoryId()))
                || validation.isNotValidWriter(board.getWriter())
                || validation.isNotValidTitle(board.getTitle())
                || validation.isNotValidContent(board.getContent())
                || validation.isNotValidPassword(board.getPassword())
                || validation.isNotPasswordMatching(board.getPassword(), confirmPassword)) {

            return "400";
        }

        boardService.writeBoard(board);

        return "redirect:/board/free/list";
    }

    //수정
    @GetMapping("/modify")
    public String getModifyInfo(
            Model model,
            @RequestParam("id") String id,
            @RequestParam("pageNum") String pageNum) {

        model.addAttribute("board", boardService.getBoardById(id));
//        model.addAttribute("commentList", commentService.getCommentByBoardId(id));
        model.addAttribute("id", id); //todo 굳이 필요한지
        model.addAttribute("pageNum", pageNum);

        return "modify";
    }

    // 수정 등록
    @PostMapping("/modify")
    public String modifyBoard(
            @ModelAttribute("board") Board board){

        log.info("board ={}", board);

        // boardId와 password가 일치하는지
        boolean isNotPassword = boardService.passwordCheck(board) == 0;

        if (isNotPassword) {
            return "400";
        }

        boardService.modifyBoard(board);

        return "redirect:/board/free/list";
    }

}
//TODO: 컨트롤러를 분류 단위-> 엔티티단위
//TODO: 서버 유효성 검사 @Valid 사용
//TODO: vue 인텔리제이 통합? VSC 따로?
// -> rest api로 변환
//스프링에선 restcontroller로 다 바꾸기
//컴퍼지션 사용
//옵저버패턴공부

//TODO 글로벌에러 핸들러에서 모든 처리. 메서드는 익셉션 던지기만