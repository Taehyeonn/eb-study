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
     * 게시글 번호에 해당하는 게시글 내용과 댓글, 파일 리스트를 출력 (API 분리 예정)
     * @param id 게시글 번호
     * @param searchFilter 검색조건
     * @param pagination 페이지네이션
     * @return ViewInfoResponse(board, commentList, fileList)
     */
    @GetMapping("/view/{id}")
    public SingleResponse<ViewInfoResponse> getViewInfo(
            @PathVariable(name = "id") String id,
            @ModelAttribute("searchFilter") SearchFilter searchFilter,
            @ModelAttribute("pagination") Pagination pagination) {

        //게시글 조회(null 일경우 서비스에서 EntityNotFoundException 예외 발생)
        Board board = boardService.getBoardById(id);

        //조회수 증가
        boardService.increaseViewCount(id);

        //댓글 리스트 조회 (분리 예정)
        List<Comment> commentList = commentService.getCommentsByBoardId(id);

        //파일 리스트 조회 (분리 예정)
        List<File> fileList = fileService.getFilesByBoardId(id);

        return responseService.getSingleResponse(new ViewInfoResponse(board, commentList, fileList));
    }

    /**
     * 카테고리, 작성자, 비밀번호, 제목, 내용을 입력 받아
     * 유효성 검사와 비밀번호 암호화 한 뒤 글 작성
     * @param board 게시글 구성 요소
     * @return HttpStatus.CREATED
     */
    @PostMapping("/boards")
    public ResponseEntity<String> writeBoard(@RequestBody @Valid Board board) {

        //비밀번호 암호화
        board.setPassword(BCrypt.hashpw(board.getPassword(), BCrypt.gensalt()));

        //게시글 작성
        boardService.writeBoard(board);

        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }



//    @GetMapping("/write")
//    public ListResponse<Category> getWriteInfo(
////            @ModelAttribute("searchFilter") SearchFilter searchFilter,
////            @ModelAttribute("pagination") Pagination pagination
//            ) {
//
//        // 카테고리 리스트 조회
//        List<Category> categoryList = categoryService.getCategoryList();
//
//        return responseService.getListResponse(categoryList);
//    }

//    @PostMapping(path = "/write", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
//    @PostMapping("/write")
//    public ResponseEntity<String> writeBoard(
//            @RequestBody @Valid Board board,
////            @RequestParam(name = "files", required = false) MultipartFile[] multipartFiles
////            @RequestPart(name = "board") Board board,
////            @RequestPart(name = "files", required = false) List<MultipartFile> multipartFiles,
////            @RequestBody(required = false) List<MultipartFile> multipartFiles,
////            @RequestBody Map<String, String> data,
//            @ModelAttribute("pagination") Pagination pagination
//            ) throws IOException {
//
//
//        //비밀번호 암호화
//        board.setPassword(BCrypt.hashpw(board.getPassword(), BCrypt.gensalt()));
//
//        //게시글 작성
//        boardService.writeBoard(board);
//        log.info("board ={}", board);
//
////        // 파일 저장
////        if (multipartFiles!=null) {
////            fileService.addFiles(multipartFiles, String.valueOf(board.getId()));
////        }
//
//        // 성공적으로 댓글이 등록되었다는 JSON 응답을 클라이언트에게 전송
//        return ResponseEntity.status(HttpStatus.CREATED).body("{\"boardId\": \"" + "board.getId()" + "\"}");
//    }


}
