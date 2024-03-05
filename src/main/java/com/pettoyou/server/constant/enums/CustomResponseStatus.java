package com.pettoyou.server.constant.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum CustomResponseStatus {

    /***
     * 1000: 요청 성공
     */
    SUCCESS (HttpStatusCode.valueOf(HttpStatus.OK.value()), "1000", "요청에 성공하였습니다.");

    private final HttpStatusCode httpStatusCode;
    private final String code;
    private final String message;

    CustomResponseStatus(HttpStatusCode httpStatusCode, String code, String message) {
        this.httpStatusCode = httpStatusCode;
        this.code = code;
        this.message = message;
    }
}
