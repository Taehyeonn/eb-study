package com.ebstudytemplates3week.Response;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    /**
     * SingleResponse로 바꿔서 출력한다.
     * @param data <T>로 바꿀 데이터
     * @param <T>
     */
    public <T> SingleResponse<T> getSingleResponse(T data) {
        SingleResponse singleResponse = new SingleResponse();
        singleResponse.data = data;
        setSuccessResponse(singleResponse);

        return singleResponse;
    }

    /**
     * ListResponse 로 바꿔서 출력한다.
     * @param dataList 바꿀 데이터
     * @return List<T>
     * @param <T>
     */
    public <T> ListResponse<T> getListResponse(List<T> dataList) {
        ListResponse listResponse = new ListResponse();
        listResponse.dataList = dataList;
        setSuccessResponse(listResponse);

        return listResponse;
    }

    /**
     * 데이터 뿌릴때 같이 들어갈 요소들
     * @param response boolean success, int code, String message
     */
    void setSuccessResponse(CommonResponse response) {
        response.code = 0;
        response.success = true;
        response.message = "SUCCESS";
    }

    /**
     * 예외 처리 response
     * @param code 예외 코드
     * @param message 예외 메세지
     * @return
     * {
     *     "success": false,
     *     "code": 코드,
     *     "message": "메세지"
     * }
     */
    public CommonResponse getErrorResponse(int code, String message) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.success = false;
        commonResponse.code = code;
        commonResponse.message = message;
        return commonResponse;
    }
}
