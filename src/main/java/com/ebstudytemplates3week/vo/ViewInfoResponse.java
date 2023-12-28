package com.ebstudytemplates3week.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ViewInfoResponse {
    private Board board;
    private List<Comment> commentList;
    private List<File> fileList;

    public ViewInfoResponse(Board board, List<Comment> commentList, List<File> fileList) {
        this.board = board;
        this.commentList = commentList;
        this.fileList = fileList;
    }
}
