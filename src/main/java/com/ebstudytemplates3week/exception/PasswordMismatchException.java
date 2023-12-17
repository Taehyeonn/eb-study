package com.ebstudytemplates3week.exception;

public class PasswordMismatchException extends RuntimeException {

    /**
     * 비밀번호 검증 실패시 예외 처리
     */
    public PasswordMismatchException() {
        super("Password mismatch");
    }

    /**
     * 비밀번호 검증 실패시 예외 처리
     * @param message 실패 메세지
     */
    public PasswordMismatchException(String message) {
        super(message);
    }

    /**
     * 비밀번호 검증 실패시 예외 처리
     * @param message 실패 메세지
     * @param cause 원인
     */
    public PasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

}
//todo 해당 예외가 발생하는 곳에 두기