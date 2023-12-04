package com.ebstudytemplates3week.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class Board {

    private int id; //게시글 번호
    private int categoryId; //카테고리 코드 1:spring 2:java 등
    private String writer;
    private String password;
    private String title;
    private String content;
    private String viewCount ; // 조회수
    private Timestamp registrationDate; //등록날짜
    private Timestamp modificationDate; //수정날짜
    private String categoryName; //카테고리명(join)
    // todo: Timestamp type
}
