package com.ebstudytemplates3week.Response;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    /**
     * @param data 데이터
     * @param <T> 제네릭
     */

    /**
     * response로 감싸서 출력한다.
     * @param data 데이터
     * @return singleResponse
     * @param <T> 제네릭
     */
    public <T> SingleResponse<T> getSingleResponse(T data) {
        SingleResponse<T> singleResponse = new SingleResponse<>();
        singleResponse.data = data;
        setSuccessResponse(singleResponse);
        return singleResponse;
    }

    /**
     * response로 감싸서 출력한다.
     * @param dataList <List>데이터
     * @return listResponse
     * @param <T> 제네릭
     */
    public <T> ListResponse<T> getListResponse(List<T> dataList) {
        ListResponse<T> listResponse = new ListResponse<>();
        listResponse.dataList = dataList;
        setSuccessResponse(listResponse);

        return listResponse;
    }

    /**
     * 데이터 뿌릴때 같이 들어갈 요소들
     * @param response boolean success, int code, String message
     * {
     *     "success": true,
     *     "code": 200,
     *     "message": SUCCESS
     * }
     */
    void setSuccessResponse(CommonResponse response) {
        response.code = 200;
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
}//todo 성공일때는 데이터만 실패일때는
