package com.ebstudytemplates3week.restcontroller;

import com.ebstudytemplates3week.Response.BoardListResponse;
import com.ebstudytemplates3week.Util.BCrypt;
import com.ebstudytemplates3week.exception.PasswordMismatchException;
import com.ebstudytemplates3week.service.BoardService;
import com.ebstudytemplates3week.service.FileService;
import com.ebstudytemplates3week.vo.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;
    private final FileService fileService;

    /**
     * 게시글 단일 조회
     * @param id 게시글 번호
     * @return board
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
    public ResponseEntity<Object> writeBoard(@Valid Board board) throws IOException { //todo

        //비밀번호 암호화
        board.setPassword(BCrypt.hashpw(board.getPassword(), BCrypt.gensalt()));

        //게시글 작성
        boardService.writeBoard(board);

        //파일 저장
        if (board.getFiles() != null) {
            fileService.addFiles(board.getFiles(), String.valueOf(board.getId()));
        }

        return ResponseEntity.created(URI.create("/boards/"+board.getId())).build();
    }

    /**
     * 게시물 리스트를 조회한다.
     * @param searchFilter 검색조건
     * @param pagination 페이지
     * @return BoardListResponse(boards, pagination)
     */
    @GetMapping("/boards")
    public ResponseEntity<BoardListResponse> getBoards(@ModelAttribute("searchFilter") SearchFilter searchFilter,
                                                       @ModelAttribute("pagination") Pagination pagination) {

        //페이지네이션
        pagination.setTotalCount(boardService.getTotalCount(searchFilter));

        //게시글 목록
        List<Board> boards = boardService.getBoardList(searchFilter, pagination);

        return ResponseEntity.ok(new BoardListResponse(boards, pagination)); //todo
    }

    /**
     * boardId에 해당하는 게시글을 비밀번호 검증 후 삭제한다.
     * @param boardId 게시를 번호
     * @param password 비밀번호
     * @return ResponseEntity.ok()
     */
    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<Object> deleteBoard(@PathVariable(name = "boardId") String boardId,
                                              @RequestParam("password") String password) {

         // 비밀번호 검증
        if (BCrypt.checkpw(password, boardService.getPassword(boardId))) {
            boardService.deleteBoard(boardId);
        } else {
            throw new PasswordMismatchException();
        }

        return ResponseEntity.ok().build();
    }
}