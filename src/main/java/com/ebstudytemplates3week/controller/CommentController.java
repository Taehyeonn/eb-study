package com.ebstudytemplates3week.controller;

import com.ebstudytemplates3week.vo.Board;
import com.ebstudytemplates3week.service.CommentService;
import com.ebstudytemplates3week.vo.Comment;
import com.ebstudytemplates3week.vo.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@Controller
@RequestMapping("/board/free/view")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     * @param boardId 게시글 번호
     * @param comment 댓글 내용(String content)
     * @param pagination pageNum
     * @return view/{게시글 번호}
     */
    @PostMapping("/{id}")
    public String insertComment(
            @PathVariable(name = "id") String boardId,
            @ModelAttribute("comment") Comment comment,
            @ModelAttribute("pagination") Pagination pagination) {

        comment.setBoardId(boardId);
        commentService.insertComment(comment);

        return "redirect:/board/free/view/"+boardId;
    }


}
