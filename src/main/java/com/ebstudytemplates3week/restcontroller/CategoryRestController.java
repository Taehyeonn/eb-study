package com.ebstudytemplates3week.restcontroller;

import com.ebstudytemplates3week.service.CategoryService;
import com.ebstudytemplates3week.vo.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    /**
     * 모든 카테고리 조회
     * @return ResponseEntity<List<Category>>
     */
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategoryList());
    }
}
