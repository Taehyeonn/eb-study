package com.ebstudytemplates3week.controller;

import com.ebstudytemplates3week.domain.Board;
import com.ebstudytemplates3week.domain.Comment;
import com.ebstudytemplates3week.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@Controller
@RequestMapping("/board/free/view")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 추가
     * @param pageNum
     * @param board id, content
     * @param ra RedirectAttributes
     * @return redirect:/board/free/view
     */
    @PostMapping
    public String insertComment(
            @RequestParam("pageNum") String pageNum,
            @ModelAttribute("board") Board board,
            RedirectAttributes ra) {

        commentService.insertComment(board);

        ra.addAttribute("id", board.getId());
        ra.addAttribute("pageNum", pageNum);

        return "redirect:/board/free/view";
    }
}
