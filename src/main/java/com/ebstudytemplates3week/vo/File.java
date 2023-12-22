package com.ebstudytemplates3week.vo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class File {

    private int id;
    private String boardId;
    private String originName; // 사용자가 지정한 원본 파일 이름
    private String reName; // 재생성한 파일 이름

    public File(String boardId, String originName, String reName) {
        this.boardId = boardId;
        this.originName = originName;
        this.reName = reName;
    }

    @Override
    public String toString() {
        return "File [boardId=" + boardId + ", originName=" + originName + ", reName=" + reName + "]";
    }
}