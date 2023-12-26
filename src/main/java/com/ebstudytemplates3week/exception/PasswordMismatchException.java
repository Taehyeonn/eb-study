package com.ebstudytemplates3week.exception;

public class PasswordMismatchException extends RuntimeException {
    /**
     * 비밀번호 검증 실패시 예외 처리
     */
    public PasswordMismatchException() {
        super();
    }
}
