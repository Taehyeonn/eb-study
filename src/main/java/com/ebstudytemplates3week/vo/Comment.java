package com.ebstudytemplates3week.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class Comment {
    private int id; //PK
    private String boardId; //FK
    private String content;
    private Timestamp registrationDate;
}
