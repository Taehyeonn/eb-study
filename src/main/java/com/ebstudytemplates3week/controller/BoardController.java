package com.ebstudytemplates3week.controller;

import com.ebstudytemplates3week.service.FileService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board/free")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final FileService fileService;


    /**
     * 검색 조건을 이용해 게시글 목록과 페이지 네비게이션 출력
     *
     * @param searchFilter    검색 조건(시작일, 종료일, 카테고리, 키워드)
     * @param pagination    페이지네이션(pageNum)
     * @param model searchFilter, pagination, boardList, categoryList
     * @return 목록 페이지
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
     * @param model 모델
     * @param id 게시글 번호
     * @param searchFilter 검색 조건
     * @param pagination 페이지
     * @return 상세 페이지
     */
    @GetMapping("/view/{id}")
    public String getViewInfo(
            Model model,
            @PathVariable(name = "id") String id,
            @ModelAttribute("searchFilter") SearchFilter searchFilter,
            @ModelAttribute("pagination") Pagination pagination) {

        log.info("searchFilter = {}", searchFilter);
        log.info("pagination = {}", pagination);

        boardService.increaseViewCount(id);

        model.addAttribute("board", boardService.getBoardById(id));
        model.addAttribute("commentList", commentService.getCommentByBoardId(id));
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchFilter", searchFilter);
        model.addAttribute("fileList", fileService.getFileByBoardId(id));


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
            @ModelAttribute("board") @Valid Board board,
            @RequestParam(name = "file", required = false) List<MultipartFile> multipartFiles) throws IOException { //todo 예외처리

        boardService.writeBoard(board);

        log.info("board ={}", board);

        for (MultipartFile file : multipartFiles) {

            if (!file.isEmpty()) {
                fileService.addFile(file, String.valueOf(board.getId()));
            }
        }

        return "redirect:/board/free/list";
    }

    /**
     * 글 수정 페이지에 필요한 정보 출력
     * @param model 모델
     * @param id 게시글 번호
     * @param searchFilter 검색조건
     * @param pagination 페이지
     * @return 수정 페이지
     */
    @GetMapping("/modify/{id}")
    public String getModifyInfo(
            Model model,
            @PathVariable(name = "id") String id,
            @ModelAttribute("searchFilter") SearchFilter searchFilter,
            @ModelAttribute("pagination") Pagination pagination) {

        model.addAttribute("board", boardService.getBoardById(id));
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchFilter", searchFilter);
        model.addAttribute("fileList", fileService.getFileByBoardId(id));

        return "modify";
    }

    /**
     * 게시글 수정
     * @param board 게시글 정보 writer, title, content
     * @return 목록
     */
    @PostMapping("/modify/{id}")
    public String modifyBoard(
            @ModelAttribute("board") @Valid Board board) {

        log.info("board ={}", board);

        boardService.modifyBoard(board); //todo 비밀번호체크는 서비스로 제공되어야 한다. 비즈니스로직의 영역

        return "redirect:/board/free/list";
    }

    /**
     * 게시글 삭제 페이지에 필요한 정보 출력
     * @param model 모델
     * @param id 게시글 번호
     * @param searchFilter 검색 조건
     * @param pagination 페이지
     * @return 삭제 페이지
     */
    @GetMapping("/delete/{id}")
    public String deleteForm(
            Model model,
            @PathVariable(name = "id") String id,
            @ModelAttribute("searchFilter") SearchFilter searchFilter,
            @ModelAttribute("pagination") Pagination pagination) {

        model.addAttribute("id", id);
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchFilter", searchFilter);

        return "delete";
    }

    /**
     * 게시글 삭제
     * @param id 게시글 번호
     * @param password 비밀번호
     * @return 목록
     */
    @PostMapping("/delete/{id}")
    public String deleteBoard(
            @PathVariable(name = "id") String id,
            @RequestParam("password") @NotBlank @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{4,16}$"
                    , message = "비밀번호는 4글자 이상 16글자 미만, 영문/숫자/특수문자(@#$%^&+=) 포함되어야 합니다.") String password) {

        boardService.deleteBoard(id, password);

        return "redirect:/board/free/list";
    }
}