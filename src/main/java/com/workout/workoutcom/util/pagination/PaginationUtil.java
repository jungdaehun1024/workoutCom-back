package com.workout.workoutcom.util.pagination;


public class PaginationUtil {

    //클라이언트에서 클릭한 페이징 버튼의 번호 (0,1,2,3...)
    private final int paginationIndex;

    //한 페이지에 들어갈 데이터 수
    private final int pageSize;

    public PaginationUtil(int paginationIndex, int pageSize) {
        this.paginationIndex = paginationIndex;
        this.pageSize = pageSize;
    }

    //오프셋 계산
    public int calculateOffset() {
        return paginationIndex * pageSize;
    }


    public int getPageSize(){
        return pageSize;
    }

    public int getPaginationIndex() {
        return paginationIndex;
    }


}
