package com.ebstudytemplates3week.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Exception {
    PASSWORD_MISMATCH(400, "비밀번호가 일치하지 않습니다."),
    INVALIDATE_INPUT(400, "입력값이 유효하지 않습니다."),
    NOT_FOUND_BOARD(404, "존재하지 않는 게시글입니다.");

    private final int code;
    private final String message;
}
