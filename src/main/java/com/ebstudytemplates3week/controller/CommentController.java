package com.ebstudytemplates3week.controller;

import com.ebstudytemplates3week.domain.Comment;
import com.ebstudytemplates3week.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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

    @PostMapping
    public String insertComment(
            @RequestParam("id") String id,
            @RequestParam("pageNum") String pageNum,
            @RequestParam("content") String content,
            RedirectAttributes ra) {

        Comment comment = new Comment();
        comment.setBoardId(id);
        comment.setContent(content);
        //TODO: 바인딩 된 채로 받기

        commentService.insertComment(comment);

        ra.addAttribute("id", id);
        ra.addAttribute("pageNum", pageNum);

        return "redirect:/board/free/view";
    }
}
