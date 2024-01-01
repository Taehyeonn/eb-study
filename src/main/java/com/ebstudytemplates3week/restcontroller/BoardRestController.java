package com.ebstudytemplates3week.restcontroller;

import com.ebstudytemplates3week.Response.ResponseService;
import com.ebstudytemplates3week.Response.SingleResponse;
import com.ebstudytemplates3week.Util.BCrypt;
import com.ebstudytemplates3week.service.BoardService;
import com.ebstudytemplates3week.service.CategoryService;
import com.ebstudytemplates3week.service.CommentService;
import com.ebstudytemplates3week.service.FileService;
import com.ebstudytemplates3week.vo.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;
    private final ResponseService responseService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final FileService fileService;

    /**
     * 검색조건과 페이지를 받아서 게시글 리스트, 페이지, 카테고리, 검색조건을 묶은 SingleResponse 반환 (API 분리 예정)
     * @param searchFilter 검색조건
     * @param pagination 페이징
     * @return BoardListResponse(boards, pagination, categoryList, searchFilter)
     */
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

    /**
     * 게시글 단일 조회
     * @param id 게시글 번호
     * @return ResponseEntity<Board>
     */
    @GetMapping("/boards/{id}")
    public ResponseEntity<Board> getBoard(
            @PathVariable(name = "id") String id) {

        //게시글 조회(리턴 값이 null 일경우 서비스에서 EntityNotFoundException 예외 던짐)
        Board board = boardService.getBoardById(id);

        //조회수 증가
        boardService.increaseViewCount(id);

        return ResponseEntity.ok(board);
    }

    /**
     * 카테고리, 작성자, 비밀번호, 제목, 내용을 입력 받아
     * 유효성 검사와 비밀번호 암호화 한 뒤 글 작성
     * @param board 게시글 구성 요소
     * @return HttpStatus.CREATED
     */
    @PostMapping(value = "/boards", consumes = "multipart/form-data")
    public ResponseEntity<String> writeBoard(Board board) throws IOException {

        //비밀번호 암호화
        board.setPassword(BCrypt.hashpw(board.getPassword(), BCrypt.gensalt()));

        //게시글 작성
        boardService.writeBoard(board);

        //파일 저장
        if (board.getFiles() != null) {
            fileService.addFiles(board.getFiles(), String.valueOf(board.getId()));
        }

        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }
}