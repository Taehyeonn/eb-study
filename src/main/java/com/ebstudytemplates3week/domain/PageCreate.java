package com.ebstudytemplates3week.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageCreate {

    private Page paging;
    private int totalCount; // 총 게시글 수
    private int beginPage; // 네비게이션의 첫번째 페이지
    private int endPage; // 네비게이션의 마지막 페이지
    private int lastPage; // 모든 게시글의 마지막 페이지
    private boolean prev;
    private boolean next;

    private final int PAGE_LIMIT = 10; // 페이지 네비게이션 수 1~10


    //페이징에 필요한 변수들 설정
    private void calcDataOfPage() {

        beginPage = (paging.getPageNum()-1) / PAGE_LIMIT * PAGE_LIMIT + 1;

        endPage = (int) (Math.ceil(paging.getPageNum() / (double) PAGE_LIMIT) * PAGE_LIMIT);

        lastPage = (int) Math.ceil((double)totalCount / paging.getLimit());

        prev = paging.getPageNum() != 1; // 지금이 1페이지면 false, 이외에는 계속 true

        next = paging.getPageNum() != lastPage; // 지금이 마지막페이지면 false, 이외에는 계속 true

        // 만약에 endPage가 lastPage보다 클 때 endPage를 lastPage로 변경
        if(endPage > lastPage) {
            endPage = lastPage;
        }
    }

    //총 게시글수가 설정될 때 페이징 요소들도 다시 설정한다
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        calcDataOfPage();
    }
}
