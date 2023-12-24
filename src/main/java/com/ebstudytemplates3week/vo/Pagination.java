package com.ebstudytemplates3week.vo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Pagination {
    private static final int PAGE_LIMIT = 10;

    //사용자가 선택한 페이지 정보를 담을 변수.
    private int pageNum; //현 페이지 넘버
    private int totalCount; // 총 게시글 수
    private int limit; // 한페이지에 보여줄 수
    private int startNum; // 게시글 시작 넘버 1페이지:0 2페이지:10
    private int beginPage; // 네비게이션의 첫번째 페이지
    private int endPage; // 네비게이션의 마지막 페이지
    private int lastPage; // 모든 게시글의 마지막 페이지
    private int pageLimit = PAGE_LIMIT; // 페이지 네비게이션의 개수 1~10
    private boolean prev;
    private boolean next;

    public Pagination() {
        this.pageNum = 1;
        this.limit = 10;
    }

    /**
     * 페이징에 필요한 변수들 설정
     * beginPage, endPage, lastPage, prev, next
     */
    private void calcDataOfPage() {

        beginPage = (pageNum-1) / pageLimit * pageLimit + 1;

        endPage = (int) (Math.ceil(pageNum / (double) pageLimit) * pageLimit);

        lastPage = (int) Math.ceil((double)totalCount / limit);

        prev = pageNum != 1; // 지금이 1페이지면 false, 이외에는 계속 true

        next = pageNum != lastPage; // 지금이 마지막페이지면 false, 이외에는 계속 true

        // 만약에 endPage가 lastPage보다 클 때 endPage를 lastPage로 변경
        if(endPage > lastPage) {
            endPage = lastPage;
        }
    }

    /**
     * 총 게시글수가 바뀔 때 페이징 요소들도 다시 설정한다
     * @param totalCount
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        calcDataOfPage();
    }

    /**
     * 바인딩용으로 변환 (검색 시작 번호)
     * @return 검색 시작 인덱스
     */
    public int getStartNum() {
        return (pageNum-1) * 10 ;
    }

    /**
     * 콘솔 log 확인용
     * @return pageNum, totalCount
     */
    @Override
    public String toString() {
        return "SearchFilter [pageNum=" + pageNum + ", totalCount=" + totalCount + "]";
    }
}
