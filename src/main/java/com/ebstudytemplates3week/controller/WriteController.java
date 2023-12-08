package com.ebstudytemplates3week.controller;

import com.ebstudytemplates3week.domain.Board;
import com.ebstudytemplates3week.domain.Category;
import com.ebstudytemplates3week.service.WriteService;
import com.ebstudytemplates3week.util.Validation;
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

    @Autowired // 생성자를 통한 의존성 주입
    public WriteController(WriteService writeService) {
        this.writeService = writeService;
    }


    // write 페이지에 필요한 카테고리, pageNum
    @GetMapping
    public String getWriteInfo(
            @RequestParam("pageNum") String pageNum,
            Model model) {

        List<Category> categoryList = writeService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("pageNum", pageNum);

        return "write";
    }

    // 유효성 검사 로직 성공시 글 등록
    @PostMapping
    public String writeBoard(
            @ModelAttribute("board") Board board,
            @RequestParam("confirmPassword") String confirmPassword){

        Validation validation = new Validation();

        log.info("board ={}", board);

        /**
         * 서버 유효성 검사 수행후 실패시 에러 페이지 발생
         */
        if (validation.isNotValidCategory(String.valueOf(board.getCategoryId()))
                || validation.isNotValidWriter(board.getWriter())
                || validation.isNotValidTitle(board.getTitle())
                || validation.isNotValidContent(board.getContent())
                || validation.isNotValidPassword(board.getPassword())
                || validation.isNotPasswordMatching(board.getPassword(), confirmPassword)) {

            return "400";
        }

        writeService.writeBoard(board);

        return "redirect:/board/free/list";
    }
}
//TODO: 컨트롤러를 페이지 단위로 나누는 게 맞는지?
//TODO: 서버 유효성 검사 @Valid ?
//TODO: vue 인텔리제이 통합? VSC 따로?