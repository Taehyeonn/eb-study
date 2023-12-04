package com.ebstudytemplates3week.controller;


import com.ebstudytemplates3week.domain.Board;
import com.ebstudytemplates3week.domain.Category;
import com.ebstudytemplates3week.domain.Page;
import com.ebstudytemplates3week.domain.PageCreate;
import com.ebstudytemplates3week.service.ListService;
import com.ebstudytemplates3week.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board/free/list")
public class ListController {

    private final ListService listService;

    @Autowired //생성자를 통한 의존성 주입
    public ListController(ListService listService) {
        this.listService = listService;
    }

    /**
     * list.html을 구성하는 요소들을 출력한다
     * 페이징, 게시글 목록, 게시글 개수
     */
    @GetMapping
//    public String getWriter(@PathVariable String id) {
    public String getList(Model model, Page page) {

        PageCreate pc = new PageCreate();
        pc.setPaging(page);
        pc.setTotalCount(listService.getTotalCount(page));

        model.addAttribute("boardList", listService.getBoardList(page));
        model.addAttribute("pc", pc);

        List<Category> categoryList = listService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        return "list";
    }
}
