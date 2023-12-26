package com.ebstudytemplates3week.Response;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResponse<T> extends CommonResponse{
    List<T> dataList;
}
