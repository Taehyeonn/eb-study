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


    //전체조회
    /**
     * 의도
     * @param model 파라미터 설명
     * @param page
     * @return 반환값설명
     */
    @GetMapping("/list")
    public String getList(Model model, Page page) {

        PageCreate pc = new PageCreate();
        pc.setPaging(page);
        pc.setTotalCount(boardService.getTotalCount(page));

        model.addAttribute("boardList", boardService.getBoardList(page));
        model.addAttribute("pc", pc);

        List<Category> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        return "list";
    }
    //todo: 쿼리랑 파라미터를 나눠서 전달,
    //todo: 검색조건과 페이지네이션 분리

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

}
//TODO: 컨트롤러를 분류 단위-> 엔티티단위
//TODO: 서버 유효성 검사 @Valid 사용
//TODO: vue 인텔리제이 통합? VSC 따로?
// -> rest api로 변환
//스프링에선 restcontroller로 다 바꾸기
//컴퍼지션 사용
//옵저버패턴공부

//TODO 글로벌에러 핸들러에서 모든 처리. 메서드는 익셉션 던지기만