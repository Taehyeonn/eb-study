package com.ebstudytemplates3week.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class Board {

    private int id; //게시글 번호
    private int categoryId; //카테고리 코드 1:spring 2:java 등

    @NotBlank
    @Size(min=3, max=5, message = "작성자는 3글자 이상, 5글자 미만이어야 합니다.")
    private String writer;

    @NotBlank
    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{4,16}$"
    , message = "비밀번호는 4글자 이상 16글자 미만, 영문/숫자/특수문자(@#$%^&+=) 포함되어야 합니다.")
    private String password;

    @NotBlank
    @Size(min=4, max=100, message = "제목은 4글자 이상, 100글자 미만이어야 합니다.")
    private String title;

    @NotBlank
    @Size(min=4, max=2000, message = "내용은 4글자 이상, 2000글자 미만이어야 합니다.")
    private String content;

    private String viewCount ; // 조회수
    private Timestamp registrationDate; //등록날짜
    private Timestamp modificationDate; //수정날짜
    private String categoryName; //카테고리명(join)
    // todo: Timestamp type
}