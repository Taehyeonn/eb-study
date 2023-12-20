package com.ebstudytemplates3week.controller;

import com.ebstudytemplates3week.util.Utils;
import com.ebstudytemplates3week.vo.*;
import com.ebstudytemplates3week.service.BoardService;
import com.ebstudytemplates3week.service.CategoryService;
import com.ebstudytemplates3week.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
     * @param searchFilter    검색 조건(시작일, 종료일, 카테고리, 키워드)
     * @param pagination    페이지네이션(pageNum)
     * @param model searchFilter, pagination, boardList, categoryList
     * @return list
     */
    @GetMapping("/list")
    public String getList(@ModelAttribute("searchFilter") SearchFilter searchFilter,
                          @ModelAttribute("pagination") Pagination pagination,
                          Model model) {

        Utils utils = new Utils();

        //검색 필터 set(view에 내려줄 날짜 데이터 set)
        if (searchFilter.getStartDate() == null) {
            searchFilter.setStartDate(String.valueOf(utils.getStartDate()));
            searchFilter.setEndDate(String.valueOf(utils.getEndDate()));
        }

        //페이지네이션
        pagination.setTotalCount(boardService.getTotalCount(searchFilter));
        model.addAttribute("pagination", pagination);

        log.info("searchFilter = {}", searchFilter);
        log.info("pagination = {}", pagination);

        //게시글 목록
        model.addAttribute("boardList", boardService.getBoardList(searchFilter, pagination));

        //카테고리 목록
        List<Category> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        return "list";
    }
    //todo: 쿼리랑 파라미터를 나눠서 전달,

    /**
     * 게시글 상세 조회
     *
     * @param model
     * @param id      board_id
     * @return view
     */
    @GetMapping("/view") //todo
    public String getViewInfo(
            Model model,
            @RequestParam("id") String id,
            @ModelAttribute("searchFilter") SearchFilter searchFilter,
            @ModelAttribute("pagination") Pagination pagination) {

        log.info("searchFilter = {}", searchFilter);
        log.info("pagination = {}", pagination);

        boardService.increaseViewCount(id);

        model.addAttribute("board", boardService.getBoardById(id));
        model.addAttribute("commentList", commentService.getCommentByBoardId(id));
        model.addAttribute("id", id); //todo 굳이 필요한지
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchFilter", searchFilter);

        return "view";
    }
    //todo: 잘못된 id,pageNum 예외처리

    /**
     * 글 작성 페이지 출력
     *
     * @param
     * @param model
     * @return write
     */
    @GetMapping("/write")
    public String getWriteInfo(
            @ModelAttribute("searchFilter") SearchFilter searchFilter,
            @ModelAttribute("pagination") Pagination pagination,
            Model model) {

        List<Category> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        return "write";
    }

    /**
     * 글 등록 버튼 클릭시 유효성 검사 후 글 작성
     *
     * @param board category_id, writer, password, title, content
     * @return redirect:/board/free/list
     */
    @PostMapping("/write")
    public String writeBoard(
            @ModelAttribute("board") @Valid Board board) {

        log.info("board ={}", board);

        boardService.writeBoard(board);

        return "redirect:/board/free/list";
    }

    /**
     * 글 작성 폼에 필요한 요소들 출력
     *
     * @param model
     * @param id      board 번호
     * @return modify
     */
    @GetMapping("/modify")
    public String getModifyInfo(
            Model model,
            @RequestParam("id") String id,
            @ModelAttribute("searchFilter") SearchFilter searchFilter,
            @ModelAttribute("pagination") Pagination pagination) {

        model.addAttribute("board", boardService.getBoardById(id));
        model.addAttribute("id", id); //todo 굳이 필요한지
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchFilter", searchFilter);

        return "modify";
    }

    /**
     * 게시글 수정(비밀번호 검증은 서비스에서)
     *
     * @param board writer, title, content
     * @return redirect:/board/free/list
     */
    @PostMapping("/modify")
    public String modifyBoard(
            @ModelAttribute("board") @Valid Board board) {

        log.info("board ={}", board);

        boardService.modifyBoard(board); //todo 비밀번호체크는 서비스로 제공되어야 한다. 비즈니스로직의 영역

        return "redirect:/board/free/list";
    }

    /**
     * 게시글 삭제 폼
     * @param model 모델
     * @param id 삭제할 보드 idx
     * @return
     */
    @GetMapping("/delete")
    public String deleteForm(
            Model model,
            @RequestParam("id") String id,
            @ModelAttribute("searchFilter") SearchFilter searchFilter,
            @ModelAttribute("pagination") Pagination pagination) {

        model.addAttribute("id", id);
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchFilter", searchFilter);

        return "delete";
    }

    /**
     * 게시글 삭제(비밀번호 검증은 서비스에서)
     * @param id
     * @param password
     * @return
     */
    @PostMapping("/delete")
    public String deleteBoard(
            @RequestParam("id") String id,
            @RequestParam("password") @NotBlank @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{4,16}$"
                    , message = "비밀번호는 4글자 이상 16글자 미만, 영문/숫자/특수문자(@#$%^&+=) 포함되어야 합니다.") String password) {

        boardService.deleteBoard(id, password);

        return "redirect:/board/free/list";
    }
}
//todo 레이어 별로 일관성있게
//컨트롤러에서는 주문접수만
//조리는 서비스
//역할을 구분하자

//도메인보다는 vo(get set만)
//싱글페이지(사용자) 멀티페이지(관리자) 두개. 각각의 의도까지