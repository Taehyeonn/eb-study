package com.ebstudytemplates3week.vo;

import lombok.Getter;

@Getter
public class PasswordVerification {
    private String id;
    private String password;

    public PasswordVerification() {
    }

    /**
     * 게시글과 비밀번호 일치 여부 확인을 위한 VO
     * @param id 게시글 번호
     * @param password 비밀번호
     */
    public PasswordVerification(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
