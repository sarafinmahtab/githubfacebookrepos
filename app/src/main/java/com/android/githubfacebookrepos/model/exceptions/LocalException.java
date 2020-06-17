package com.android.githubfacebookrepos.model.exceptions;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

/**
 * LocalException used for readability of internal error
 */
public class LocalException extends Exception {

    private int errorCode = 400;

    public LocalException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public LocalException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
