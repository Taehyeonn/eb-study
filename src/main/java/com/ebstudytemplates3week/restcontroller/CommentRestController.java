package com.ebstudytemplates3week.restcontroller;

import com.ebstudytemplates3week.Response.ListResponse;
import com.ebstudytemplates3week.Response.ResponseService;
import com.ebstudytemplates3week.service.CommentService;
import com.ebstudytemplates3week.vo.Comment;
import com.ebstudytemplates3week.vo.CommentResponse;
import com.ebstudytemplates3week.vo.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;
    private final ResponseService responseService;

    @PostMapping("/view/{id}")
    public ResponseEntity<String> insertComment(
            @PathVariable(name = "id") String boardId,
            @RequestBody Map<String, String> data,
            @ModelAttribute("pagination") Pagination pagination) {

        commentService.insertComment(new Comment(boardId, data.get("content")));

        // 성공적으로 댓글이 등록되었다는 JSON 응답을 클라이언트에게 전송
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"boardId\": \"" + boardId + "\"}");
    }

    @GetMapping("/comments/{id}")
    public ListResponse<Comment> getComments(
            @PathVariable(name = "id") String boardId) {

        List<Comment> comments = commentService.getCommentsByBoardId(boardId);

        return responseService.getListResponse(comments);
    }

    @PostMapping("/comments/{id}")
    public ResponseEntity<String> postComment(
            @PathVariable(name = "id") String boardId,
            @RequestBody CommentResponse commentResponse) {

        log.info("commentResponse={}",commentResponse);

        CommentResponse.DataObject data = commentResponse.getData();

        commentService.insertComment(new Comment(data.getBoardId(), data.getContent()));

        // 성공적으로 댓글이 등록되었다는 JSON 응답을 클라이언트에게 전송
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"boardId\": \"" + boardId + "\"}");
    }
}
