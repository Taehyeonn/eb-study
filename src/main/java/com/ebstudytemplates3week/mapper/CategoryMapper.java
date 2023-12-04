package com.ebstudytemplates3week.mapper;

import com.ebstudytemplates3week.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    // 전체 카테고리 출력
    List<Category> getCategoryList();
}
