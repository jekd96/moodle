package com.moodle.moodle.exception;

import lombok.Getter;

@Getter
public class CommonException extends Exception {

    private final String errorMessage;

    public CommonException(String errorMessage, Exception e) {
        super(e);
        this.errorMessage = errorMessage;
    }

}
