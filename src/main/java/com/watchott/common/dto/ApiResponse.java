package com.watchott.common.dto;

import lombok.*;

/**
 * packageName    : com.watchott.common.dto
 * fileName       : ApiResponse
 * author         : shipowner
 * date           : 2025-06-14
 * description    :
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    public enum ResponseStatus {
        SUCCESS, FAIL, ERROR
    }

    private ResponseStatus status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResponseStatus.SUCCESS, null, data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(ResponseStatus.SUCCESS, message, null);
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(ResponseStatus.FAIL, message, null);
    }

    public static <T> ApiResponse<T> error() {
        return new ApiResponse<>(ResponseStatus.ERROR, "서버 오류가 발생했습니다. 다시 시도해주세요.", null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(ResponseStatus.ERROR, message, null);
    }

}
