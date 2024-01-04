package com.ebstudytemplates3week.restcontroller;

import com.ebstudytemplates3week.service.CommentService;
import com.ebstudytemplates3week.vo.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    /**
     * 게시글 번호에 해당하는 댓글 내용 조회
     * @param boardId 게시글 번호
     * @return ListResponse<Comment>
     */
    @GetMapping("/comments/{boardId}")
    public ResponseEntity<List<Comment>> getComments(
            @PathVariable(name = "boardId") String boardId) {

        //댓글 목록 조회
        List<Comment> comments = commentService.getCommentsByBoardId(boardId);

        return ResponseEntity.ok(comments);
    }

    /**
     * 게시글 번호와 댓글 내용을 입력 받아 작성하는 메서드
     * @param boardId 게시글 번호
     * @param comment 게시글 번호, 댓글 내용
     * @return HttpStatus.CREATED
     */
    @PostMapping("/comments/{boardId}")
    public ResponseEntity<Object> postComment(
            @PathVariable(name = "boardId") String boardId,
            @RequestBody Comment comment) {

        // 댓글 작성
        commentService.insertComment(comment);

        return ResponseEntity.created(URI.create("/comments/"+boardId)).build();
    }
}
