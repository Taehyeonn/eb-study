package com.ebstudytemplates3week.domain;

import com.ebstudytemplates3week.util.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Page {
    Utils utils = new Utils();

    //사용자가 선택한 페이지 정보를 담을 변수.
    private int pageNum; //현 페이지 넘버
    private int limit; // 한페이지에 보여줄 수

    //검색에 필요한 데이터를 변수로 선언.
    private int category;
    private int startNum; // 게시글 시작 넘버 1페이지:0 2페이지:10
    private String searchText;
    private String startDate; // 검색 시작 날짜
    private String endDate; // 검색 종료 날짜
    private final String START_TIME = "00:00:00";
    private final String END_TIME = "23:59:59";

    public Page() {
        this.pageNum = 1;
        this.limit = 10;
        this.startDate = String.valueOf(utils.getStartDate());
        this.endDate = String.valueOf(utils.getEndDate());
        this.category = 0;
    }

    //바인딩용으로 변환 (yyyy-mm-dd hh:mm:ss)
    public String getStartDate() {
        return startDate + " " + START_TIME;
    }

    //바인딩용으로 변환 (yyyy-mm-dd hh:mm:ss)
    public String getEndDate() {
        return endDate + " " + END_TIME;
    }

    //바인딩용으로 변환 (검색 시작 번호)
    public int getStartNum() {
        return (pageNum-1) * 10 ;
    }
}
