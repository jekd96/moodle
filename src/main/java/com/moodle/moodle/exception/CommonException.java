package com.moodle.moodle.exception;

import lombok.Getter;

/**
 * Общий эксепшен приложения
 */
@Getter
public class CommonException extends Exception {

    private final String errorMessage;

    public CommonException(String errorMessage, Exception e) {
        super(e);
        this.errorMessage = errorMessage;
    }

}
