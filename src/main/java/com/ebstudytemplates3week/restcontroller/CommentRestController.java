package com.ebstudytemplates3week.restcontroller;

import com.ebstudytemplates3week.Response.ListResponse;
import com.ebstudytemplates3week.Response.ResponseService;
import com.ebstudytemplates3week.service.CommentService;
import com.ebstudytemplates3week.vo.Comment;
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
public class CommentRestController {

    private final CommentService commentService;
    private final ResponseService responseService;

    /**
     * 게시글 번호에 해당하는 댓글 내용 조회
     * @param boardId 게시글 번호
     * @return ListResponse<Comment>
     */
    @GetMapping("/comments/{boardId}")
    public ListResponse<Comment> getComments(
            @PathVariable(name = "boardId") String boardId) {

        //댓글 목록 조회
        List<Comment> comments = commentService.getCommentsByBoardId(boardId);

        return responseService.getListResponse(comments);
    }

    /**
     * 게시글 번호와 댓글 내용을 입력 받아 작성하는 메서드
     * @param boardId 게시글 번호
     * @param comment 게시글 번호, 댓글 내용
     * @return HttpStatus.CREATED
     */
    @PostMapping("/comments/{boardId}")
    public ResponseEntity<String> postComment(
            @PathVariable(name = "boardId") String boardId,
            @RequestBody Comment comment) {

        // 댓글 작성
        commentService.insertComment(comment);

        // 성공적으로 댓글이 등록되었다는 JSON 응답을 클라이언트에게 전송
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"boardId\": \"" + boardId + "\"}");
    }
}
