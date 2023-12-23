package com.ebstudytemplates3week.vo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchFilter {

    private int category;
    private String searchText;
    private String startDate; // 검색 시작 날짜
    private String endDate; // 검색 종료 날짜
    private final String START_TIME = "00:00:00"; //todo 스네이크는 상수 정의할 때
    private final String END_TIME = "23:59:59";

    /**
     * 콘솔 Log 확인용
     * @return SearchFilter
     */
    @Override
    public String toString() {
        return "SearchFilter [category=" + category + ", searchText=" + searchText + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
}
