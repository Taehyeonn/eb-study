package com.ebstudytemplates3week.restcontroller;

import com.ebstudytemplates3week.Response.ListResponse;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

        List<Comment> commentList = commentService.getCommentsByBoardId(id);

        List<File> fileList = fileService.getFilesByBoardId(id);

        return responseService.getSingleResponse(new ViewInfoResponse(board, commentList, fileList));
    }


    @GetMapping("/write")
    public ListResponse<Category> getWriteInfo(
//            @ModelAttribute("searchFilter") SearchFilter searchFilter,
//            @ModelAttribute("pagination") Pagination pagination
            ) {

        // 카테고리 리스트 조회
        List<Category> categoryList = categoryService.getCategoryList();

        return responseService.getListResponse(categoryList);
    }

//    @PostMapping(path = "/write", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PostMapping("/write")
    public ResponseEntity<String> writeBoard(
            @RequestBody @Valid Board board,
//            @RequestParam(name = "files", required = false) MultipartFile[] multipartFiles
//            @RequestPart(name = "board") Board board,
//            @RequestPart(name = "files", required = false) List<MultipartFile> multipartFiles,
//            @RequestBody(required = false) List<MultipartFile> multipartFiles,
//            @RequestBody Map<String, String> data,
            @ModelAttribute("pagination") Pagination pagination
            ) throws IOException {


        //비밀번호 암호화
        board.setPassword(BCrypt.hashpw(board.getPassword(), BCrypt.gensalt()));

        //게시글 작성
        boardService.writeBoard(board);
        log.info("board ={}", board);

//        // 파일 저장
//        if (multipartFiles!=null) {
//            fileService.addFiles(multipartFiles, String.valueOf(board.getId()));
//        }

        // 성공적으로 댓글이 등록되었다는 JSON 응답을 클라이언트에게 전송
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"boardId\": \"" + "board.getId()" + "\"}");
    }
}
