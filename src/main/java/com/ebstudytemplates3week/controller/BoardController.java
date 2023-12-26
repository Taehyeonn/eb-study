package com.ebstudytemplates3week.controller;

import com.ebstudytemplates3week.Util.BCrypt;
import com.ebstudytemplates3week.exception.PasswordMismatchException;
import com.ebstudytemplates3week.service.FileService;
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
import java.util.ArrayList;
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
        model.addAttribute("fileList", fileService.getFilesByBoardId(id));

        return "view";
    }
    //todo: 잘못된 id,pageNum 예외처리

    /**
     * 글 작성 페이지 출력
     * @param searchFilter 검색 조건
     * @param pagination 페이지
     * @param model 모델
     * @return 글 작성 페이지
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

        //비밀번호 암호화
        String encryptedPassword = BCrypt.hashpw(board.getPassword(), BCrypt.gensalt());
        board.setPassword(encryptedPassword);

        //게시글 작성
        boardService.writeBoard(board);
        log.info("board ={}", board);

        // 파일 저장
        if (multipartFiles!=null) {
            fileService.addFiles(multipartFiles, String.valueOf(board.getId()));
        }
//        //파일 저장
//        for (MultipartFile file : multipartFiles) {
//            if (!file.isEmpty()) {
//                log.info("================");
//                log.info("업로드 파일명" + file.getOriginalFilename());
//                log.info("업로드 파일크기" + file.getSize());
//                fileService.addFile(file, String.valueOf(board.getId()));
//            }
//        }

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
        model.addAttribute("fileList", fileService.getFilesByBoardId(id));

        return "modify";
    }

    /**
     * 비밀번호 일치시 게시글 수정
     * @param board 게시글 정보 writer, title, content
     * @return 목록
     */
    @PostMapping("/modify/{id}")
    public String modifyBoard(
            @ModelAttribute("board") @Valid Board board,
            @RequestParam(name = "fileId", required = false) List<String> fileIds,
            @RequestParam(name = "newFile", required = false) List<MultipartFile> newMultipartFiles) throws IOException {

        log.info("board ={}", board);
        log.info("fileIds ={}", fileIds);
        log.info("newMultipartFiles ={}", newMultipartFiles);

        // 비밀번호 검증
        if (BCrypt.checkpw(board.getPassword(), boardService.getPassword(String.valueOf(board.getId())))) {
            boardService.modifyBoard(board);
        } else {
            throw new PasswordMismatchException();
        }

        List<String> storedFileIds = fileService.getFileIdsByBoardId(String.valueOf(board.getId()));
        List<String> deletedFileIds = fileService.getDeletedFileIds(fileIds, storedFileIds);

        for (String fileId : deletedFileIds) {
            if (!fileId.isEmpty()) {
                log.info("================");
                log.info("삭제할 파일 번호: ={}", fileId);
                File file = fileService.getFileInfoByFileId(fileId);
                fileService.deleteFile(file);
            }
        }

        // 파일 저장
        if (newMultipartFiles!=null) {
            fileService.addFiles(newMultipartFiles, String.valueOf(board.getId()));
        }

        return "redirect:/board/free/list";
    }

    /**
     * 게시글 삭제 페이지에 필요한 정보 출력
     * @param id 페이지 번호(view 버튼에 사용)
     * @param searchFilter 검색 조건
     * @param pagination 페이지
     * @return 삭제 페이지
     */
    @GetMapping("/delete/{id}")
    public String deleteForm(
            @PathVariable(name = "id") String id,
            @ModelAttribute("searchFilter") SearchFilter searchFilter,
            @ModelAttribute("pagination") Pagination pagination) {

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

        // 비밀번호 검증
        if (BCrypt.checkpw(password, boardService.getPassword(id))) {
            boardService.deleteBoard(id);
        } else {
            throw new PasswordMismatchException();
        }

        return "redirect:/board/free/list";
    }
}