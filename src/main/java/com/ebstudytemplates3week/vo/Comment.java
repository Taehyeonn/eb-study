package com.ebstudytemplates3week.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter @Setter @ToString
public class Comment {
    private int id; //PK
    private String boardId; //FK
    private String content;
    private Timestamp registrationDate;

    public Comment() {
    }

    public Comment(String boardId, String content) {
        this.boardId = boardId;
        this.content = content;
    }
}
