package com.ebstudytemplates3week.vo;

import com.ebstudytemplates3week.util.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchFilter {

    Utils utils = new Utils();

    private int category;
    private String searchText;
    private String startDate; // 검색 시작 날짜
    private String endDate; // 검색 종료 날짜
    private final String START_TIME = "00:00:00";
    private final String END_TIME = "23:59:59";

    public SearchFilter() {
    }

    public SearchFilter(int category, String searchText, String startDate, String endDate) {
        this.category = category;
        this.searchText = searchText;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
