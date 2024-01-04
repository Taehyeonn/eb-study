package com.ebstudytemplates3week.Response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private String message;

    /**
     * 예외 발생시 출력할 메세지
     * @param message 메세지
     */
    public ErrorResponse(String message) {
        this.message = message;
    }
}
