package com.ebstudytemplates3week;

import com.ebstudytemplates3week.Response.CommonResponse;
import com.ebstudytemplates3week.Response.ResponseService;
import com.ebstudytemplates3week.exception.Exception;
import com.ebstudytemplates3week.exception.EntityNotFoundException;
import com.ebstudytemplates3week.exception.PasswordMismatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ResponseService responseService;

    /**
     * 입력값이 유효하지 않을 경우 예외 처리(@Valid)
     * @param e MethodArgumentNotValidException
     * @return 400, "입력값이 유효하지 않습니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse handleValidationException(MethodArgumentNotValidException e) {

        log.info(e.getMessage());
        return responseService.getErrorResponse(Exception.INVALIDATE_INPUT.getCode(), Exception.INVALIDATE_INPUT.getMessage());
    }

    /**
     * 비밀번호가 일치하지 않을 경우 예외 처리
     * @param e PasswordMismatchException
     * @return 400, "비밀번호가 일치하지 않습니다.
     */
    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse handlePasswordMismatchException(PasswordMismatchException e) {
        log.info(e.getMessage());
        return responseService.getErrorResponse(Exception.PASSWORD_MISMATCH.getCode(), Exception.PASSWORD_MISMATCH.getMessage());
    }

    /**
     * 잘못된 Board id를 조회할 때 예외 처리
     * @param e EntityNotFoundException
     * @return 404 존재하지 않는 게시글입니다.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse invalidateBoardException(EntityNotFoundException e) {
        log.info(e.getMessage());
        return responseService.getErrorResponse(Exception.NOT_FOUND_BOARD.getCode(), Exception.NOT_FOUND_BOARD.getMessage());
    }
}
