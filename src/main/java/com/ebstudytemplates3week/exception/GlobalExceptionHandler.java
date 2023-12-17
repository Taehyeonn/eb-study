package com.ebstudytemplates3week.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 입력값이 유효하지 않을 경우 예외 처리(@Valid)
     * @param ex MethodArgumentNotValidException
     * @return HttpStatus.BAD_REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        // 검사된 필드 오류가 있는지 확인
        if (bindingResult.hasErrors()) {
            // 여러 오류 중 첫번째 오류 메시지 추출(getFieldErrors 가 null인 경우 방지)
            String errorMessage = bindingResult.getFieldErrors().get(0).getDefaultMessage();
            return new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
        } else {
            // 검사된 필드 오류가 없는 경우 기본 메시지 반환
            return new ErrorResponse("Validation failed", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 비밀번호가 일치하지 않을 경우 예외 처리
     * @param ex
     * @return HttpStatus.BAD_REQUEST
     */
    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePasswordMismatchException(PasswordMismatchException ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 500 예외 처리
     * @param ex
     * @return HttpStatus.INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        return new ErrorResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    static class ErrorResponse {
        private String message;
        private HttpStatus status;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        public ErrorResponse(String message, HttpStatus status) {
            this.message = message;
            this.status = status;
        }
    }
}
//todo 위치조정