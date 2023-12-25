package com.ebstudytemplates3week.vo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class File {

    private int id;
    private String boardId;
    private String originName; // 사용자가 지정한 원본 파일 이름
    private String storedName; // 재생성한 파일 이름
    private long size;

    public File() {
    }

    public File(String boardId, String originName, String storedName, long size) {
        this.boardId = boardId;
        this.originName = originName;
        this.storedName = storedName;
        this.size = size;
    }

    @Override
    public String toString() {
        return "File [boardId=" + boardId + ", originName=" + originName + ", storedName=" + storedName + "]";
    }
}