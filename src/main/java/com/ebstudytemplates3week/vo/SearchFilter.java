package com.ebstudytemplates3week.vo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchFilter {
    private static final String START_TIME = "00:00:00";
    private static final String END_TIME = "23:59:59";

    private int category;
    private String searchText;
    private String startDate; // 검색 시작 날짜
    private String endDate; // 검색 종료 날짜
    private String startTime = START_TIME;
    private String endTime = END_TIME;

    /**
     * 콘솔 Log 확인용
     * @return SearchFilter
     */
    @Override
    public String toString() {
        return "SearchFilter [category=" + category + ", searchText=" + searchText + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
}
