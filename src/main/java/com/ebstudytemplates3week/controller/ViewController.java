package com.ebstudytemplates3week.controller;

import com.ebstudytemplates3week.domain.Page;
import com.ebstudytemplates3week.service.ViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/board/free/view")
public class ViewController {

    private final ViewService viewService;

    @Autowired
    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping
    public String getViewInfo(
            Model model,
            @RequestParam("id") String id,
            @RequestParam("pageNum") String pageNum) {

        viewService.increaseViewCount(id);

        model.addAttribute("board", viewService.getBoardById(id));
        model.addAttribute("id", id);
        model.addAttribute("pageNum", pageNum);

        return "view";
    }
}
