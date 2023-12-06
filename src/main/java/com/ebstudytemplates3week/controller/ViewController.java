package com.ebstudytemplates3week.controller;

import com.ebstudytemplates3week.domain.Comment;
import com.ebstudytemplates3week.service.ViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/board/free/view")
public class ViewController {

    private final ViewService viewService;

    @Autowired
    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    /**
     * GET 방식으로 조회시 view 를 구성하는 요소들을 출력
     * board, comment, 페이징 요소 등
     */
    @GetMapping
    public String getViewInfo(
            Model model,
            @RequestParam("id") String id,
            @RequestParam("pageNum") String pageNum) {

        viewService.increaseViewCount(id);

        model.addAttribute("board", viewService.getBoardById(id));
        model.addAttribute("commentList", viewService.getCommentByBoardId(id));
        model.addAttribute("id", id);
        model.addAttribute("pageNum", pageNum);

        return "view";
    }

    /**
     * view 에서 등록 버튼 누를시
     * 댓글 등록 후 view 로 redirect
     */
    @PostMapping
    public String insertComment(
            @RequestParam("id") String id,
            @RequestParam("pageNum") String pageNum,
            @RequestParam("content") String content,
            RedirectAttributes ra) {

        Comment comment = new Comment();
        comment.setBoardId(id);
        comment.setContent(content);

        viewService.insertComment(comment);

        ra.addAttribute("id", id);
        ra.addAttribute("pageNum", pageNum);

        return "redirect:/board/free/view";
    }
}