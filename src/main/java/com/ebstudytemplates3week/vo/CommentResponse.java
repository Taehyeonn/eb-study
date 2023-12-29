package com.ebstudytemplates3week.vo;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class CommentResponse {
    private DataObject data;

    @Getter @ToString
    public static class DataObject {
        private String boardId;
        private String content;

    }

}
