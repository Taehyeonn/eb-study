package com.ebstudytemplates3week.util;

/**
 * 파라미터의 유효성 검증 후 통과시 TRUE 반환
 */
public class Validation {

    // 작성자가 공백이거나 3글자 이상 5글자 미만이면 true 반환
    public boolean isNotValidWriter(String writer) {
        return writer == null || writer.trim().isEmpty() || writer.length() < 3 || writer.length() >= 5;
    }

    // 비밀번호에 영문, 숫자, 특수문자가 포함되어 있지 않거나 길이가 4글자 이상, 16글자 미만이 아니면 true 반환
    public boolean isNotValidPassword(String password) {
        return password == null || password.trim().isEmpty() || password.length() < 4 || password.length() >= 16 ||
                !password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=]).*$");
    }

    // 두 비밀번호가 일치하지 않으면 true 반환
    public boolean isNotPasswordMatching(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }

    // 카테고리를 고르지 않았으면 true 반환
    public boolean isNotValidCategory(String category) {
        return category == null || category.trim().isEmpty();
    }

    // 제목이 공백이거나 4글자 이상, 16글자 미만이면 true 반환
    public boolean isNotValidTitle(String title) {
        return title == null || title.trim().isEmpty() || title.length() < 4 || title.length() >= 100;
    }

    // 내용이 공백이거나 4글자 이상, 2000글자 미만이면 true 반환
    public boolean isNotValidContent(String content) {
        return content == null || content.trim().isEmpty() || content.length() < 4 || content.length() >= 2000;
    }
}
