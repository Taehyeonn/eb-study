package com.ebstudytemplates3week.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

    /**
     * list 달력에 들어갈 1년전 날짜 구하기
     * return yyyy-mm-dd
     */
    public LocalDate getStartDate() {
        LocalDate now = LocalDate.now();
        return now.minusYears(1); //
    }

    /**
     * list 달력에 들어갈 오늘 날짜 구하기
     * return yyyy-mm-dd
     */
    public LocalDate getEndDate() {
        return LocalDate.now();
    }
}
