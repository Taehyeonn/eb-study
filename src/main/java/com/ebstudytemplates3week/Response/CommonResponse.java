package com.ebstudytemplates3week.Response;

import lombok.Getter;

@Getter
public class CommonResponse {
    boolean success;
    int code;
    String message;
}
