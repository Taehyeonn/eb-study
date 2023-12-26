package com.ebstudytemplates3week.Response;

import lombok.Getter;

@Getter
public class SingleResponse<T> extends CommonResponse {
    T data;
}