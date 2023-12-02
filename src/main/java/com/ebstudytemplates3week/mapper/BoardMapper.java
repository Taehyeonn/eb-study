package com.ebstudytemplates3week.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardMapper {
    String getWriter(@Param("id") int id);

}
