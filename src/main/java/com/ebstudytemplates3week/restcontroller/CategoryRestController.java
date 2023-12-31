package com.ebstudytemplates3week.restcontroller;

import com.ebstudytemplates3week.Response.ListResponse;
import com.ebstudytemplates3week.Response.ResponseService;
import com.ebstudytemplates3week.service.CategoryService;
import com.ebstudytemplates3week.vo.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;
    private final ResponseService responseService;

    /**
     * 모든 카테고리 조회
     * @return ListResponse<Category>
     */
    @GetMapping("/categories")
    public ListResponse<Category> getCategories() {
        return responseService.getListResponse(categoryService.getCategoryList());
    }
}
