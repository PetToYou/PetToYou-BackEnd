package com.pettoyou.server.constant.exception;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private final CustomResponseStatus customResponseStatus;
}
