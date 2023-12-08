package com.ebstudytemplates3week.util;

/**
 * 파라미터의 유효성 검증 후 통과시 TRUE 반환
 */
public class Validation {

    public boolean isNotValidWriter(String writer) {
        return writer == null || writer.trim().isEmpty() || writer.length() < 3 || writer.length() >= 5;
    }

    public boolean isNotValidTitle(String password) {
        return password == null || password.trim().isEmpty() || password.length() < 4 || password.length() >= 16 ||
                !password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=]).*$");
    }

    public boolean isNotPasswordMatching(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }

    public boolean isNotValidCategory(String category) {
        return category == null || category.trim().isEmpty();
    }

    public boolean isNotValidPassword(String title) {
        return title == null || title.trim().isEmpty() || title.length() < 4 || title.length() >= 100;
    }

    public boolean isNotValidContent(String content) {
        return content == null || content.trim().isEmpty() || content.length() < 4 || content.length() >= 2000;
    }
}
