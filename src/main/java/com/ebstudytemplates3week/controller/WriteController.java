package com.ebstudytemplates3week.controller;

import com.ebstudytemplates3week.domain.Board;
import com.ebstudytemplates3week.domain.Category;
import com.ebstudytemplates3week.domain.File;
import com.ebstudytemplates3week.service.WriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/board/free/write")
public class WriteController {

    private final WriteService writeService;

    @Autowired //생성자를 통한 의존성 주입
    public WriteController(WriteService writeService) {
        this.writeService = writeService;
    }

    @GetMapping
    public String getWriteInfo(
            @RequestParam("pageNum") String pageNum,
            Model model) {

        List<Category> categoryList = writeService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("pageNum", pageNum);

        return "write";
    }

    @PostMapping
    public String postBoard(
            @ModelAttribute("board") Board board){

        log.info("board ={}", board);

        writeService.writeBoard(board);

        return "redirect:/board/free/list";
    }
}
